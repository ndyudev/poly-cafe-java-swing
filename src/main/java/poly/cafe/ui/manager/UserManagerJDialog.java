package poly.cafe.ui.manager;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import poly.cafe.dao.UserDAO;
import poly.cafe.dao.impl.UserDAOImpl;
import poly.cafe.entity.User;
import poly.cafe.util.XJdbc;
import javax.swing.JOptionPane;

public final class UserManagerJDialog extends javax.swing.JFrame implements CrudController<User> {

    private UserDAO userDAO = new UserDAOImpl();
    private List<User> userList;
    private int currentRow = -1;

    public UserManagerJDialog() {
        initComponents();
        XJdbc.openConnection();
        open();
    }

    public UserManagerJDialog(JFrame parent, boolean b) {

    }

    @Override
    public void open() {
        tabsUser.setSelectedIndex(0); // Chọn tab "DANH SÁCH" mặc định
        setLocationRelativeTo(null);
        fillToTable();
        loadUserListData(); // Khởi tạo userList và hiển thị user đầu tiên
    }

    @Override
    public void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tblUsers.getModel();
        model.setRowCount(0); // clear table

        try {
            userList = userDAO.findAll(); // Gán giá trị cho userList
            for (User u : userList) {
                Object[] row = {
                    u.getUsername(),
                    u.getPassword(),
                    u.getFullname(),
                    u.getPhoto(),
                    u.isManager() ? "Quản lý" : "Nhân viên",
                    u.isEnabled() ? "Hoạt động" : "Tạm dừng",
                    false // Cột "Select" mặc định là false
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu: " + e.getMessage());
        }
    }

    @Override
    public void checkAll() {
        DefaultTableModel model = (DefaultTableModel) tblUsers.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(true, i, 6); // Cột "Select" (index 6)
        }
    }

    @Override
    public void uncheckAll() {
        DefaultTableModel model = (DefaultTableModel) tblUsers.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(false, i, 6); // Cột "Select"
        }
    }

    @Override
    public void deleteCheckedItems() {
        DefaultTableModel model = (DefaultTableModel) tblUsers.getModel();
        StringBuilder errorMessages = new StringBuilder();
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            Boolean isSelected = (Boolean) model.getValueAt(i, 6);
            if (Boolean.TRUE.equals(isSelected)) {
                String username = (String) model.getValueAt(i, 0);
                try {
                    userDAO.delete(username);
                    model.removeRow(i);
                } catch (Exception e) {
                    errorMessages.append("Lỗi xóa user ").append(username).append(": ").append(e.getMessage()).append("\n");
                }
            }
        }
        fillToTable();
        loadUserListData();
        if (errorMessages.length() > 0) {
            JOptionPane.showMessageDialog(this, "Một số user không thể xóa:\n" + errorMessages.toString());
        }
    }

    void loadUserListData() {
        try {
            userList = userDAO.findAll();
            if (!userList.isEmpty()) {
                currentRow = 0;
                setForm(userList.get(currentRow)); // Hiển thị user đầu tiên
            } else {
                clear();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải danh sách user: " + e.getMessage());
        }
    }

    @Override
    public void setForm(User user) {
        if (user == null) {
            return;
        }
        txtUsername.setText(user.getUsername());
        txtPassword.setText(user.getPassword());
        txtConfirmPassword.setText(user.getPassword());
        txtFullname.setText(user.getFullname());
        chkManager.setSelected(user.isManager());
        chkEmployee.setSelected(!user.isManager());
        chkActive.setSelected(user.isEnabled());
        chkInactive.setSelected(!user.isEnabled());
        tabsUser.setSelectedIndex(1); // Chuyển sang tab "Biểu mẫu"
    }

    @Override
    public User getForm() {
        User user = new User();
        user.setUsername(txtUsername.getText());
        user.setPassword(txtPassword.getText());
        user.setFullname(txtFullname.getText());
        user.setPhoto("default.jpg"); // Gán giá trị mặc định, cần xử lý nếu có upload ảnh
        user.setManager(chkManager.isSelected());
        user.setEnabled(chkActive.isSelected());
        return user;
    }

    @Override
    public void clear() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
        txtFullname.setText("");
        chkManager.setSelected(false);
        chkEmployee.setSelected(false);
        chkActive.setSelected(false);
        chkInactive.setSelected(false);
        currentRow = -1;
        setEditable(true); // Cho phép chỉnh sửa form
    }

    @Override
    public void create() {
        if (!validateUserForm()) {
            return;
        }
        User user = getForm();
        try {
            userDAO.insert(user);
            fillToTable();
            loadUserListData();
            JOptionPane.showMessageDialog(this, "Thêm user thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi thêm user: " + e.getMessage());
        }
    }

    @Override
    public void update() {
        if (!validateUserForm()) {
            return;
        }
        User user = getForm();
        try {
            userDAO.update(user);
            fillToTable();
            loadUserListData();
            JOptionPane.showMessageDialog(this, "Cập nhật user thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi cập nhật user: " + e.getMessage());
        }
    }

    @Override
    public void delete() {
        String username = txtUsername.getText();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đăng nhập!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa user này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                userDAO.delete(username);
                fillToTable();
                loadUserListData();
                clear();
                JOptionPane.showMessageDialog(this, "Xóa user thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi xóa user: " + e.getMessage());
            }
        }
    }

    @Override
    public void edit() {
        int selectedRow = tblUsers.getSelectedRow();
        if (selectedRow >= 0) {
            currentRow = selectedRow;
            User user = userList.get(currentRow);
            setForm(user);
            setEditable(true);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một user từ bảng!");
        }
    }

    @Override
    public void setEditable(boolean editable) {
        txtUsername.setEnabled(editable);
        txtPassword.setEnabled(editable);
        txtConfirmPassword.setEnabled(editable);
        txtFullname.setEnabled(editable);
        chkManager.setEnabled(editable);
        chkEmployee.setEnabled(editable);
        chkActive.setEnabled(editable);
        chkInactive.setEnabled(editable);
        btnNew.setEnabled(editable);
        btnUpdate.setEnabled(editable);
        btnDelete.setEnabled(editable);
        btnReset.setEnabled(editable);
    }

    @Override
    public void moveFirst() {
        if (userList != null && !userList.isEmpty()) {
            currentRow = 0;
            setForm(userList.get(currentRow));
        }
    }

    @Override
    public void movePrevious() {
        if (userList != null && currentRow > 0) {
            currentRow--;
            setForm(userList.get(currentRow));
        }
    }

    @Override
    public void moveNext() {
        if (userList != null && currentRow < userList.size() - 1) {
            currentRow++;
            setForm(userList.get(currentRow));
        }
    }

    @Override
    public void moveLast() {
        if (userList != null && !userList.isEmpty()) {
            currentRow = userList.size() - 1;
            setForm(userList.get(currentRow));
        }
    }

    @Override
    public void moveTo(int rowIndex) {
        if (userList != null && rowIndex >= 0 && rowIndex < userList.size()) {
            currentRow = rowIndex;
            setForm(userList.get(currentRow));
        }
    }

    boolean validateUserForm() {
        if (txtUsername.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập không được để trống!");
            return false;
        }
        if (txtPassword.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống!");
            return false;
        }
        if (!txtPassword.getText().equals(txtConfirmPassword.getText())) {
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!");
            return false;
        }
        if (txtFullname.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ và tên không được để trống!");
            return false;
        }
        if (!chkManager.isSelected() && !chkEmployee.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn vai trò!");
            return false;
        }
        if (chkManager.isSelected() && chkEmployee.isSelected()) {
            JOptionPane.showMessageDialog(this, "Chỉ được chọn một vai trò (Quản lý hoặc Nhân viên)!");
            return false;
        }
        if (!chkActive.isSelected() && !chkInactive.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn trạng thái!");
            return false;
        }
        if (chkActive.isSelected() && chkInactive.isSelected()) {
            JOptionPane.showMessageDialog(this, "Chỉ được chọn một trạng thái (Hoạt động hoặc Tạm dừng)!");
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabsUser = new javax.swing.JTabbedPane();
        pnlList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsers = new javax.swing.JTable();
        btnSelectAll = new javax.swing.JButton();
        btnUnselectAll = new javax.swing.JButton();
        btnClearSelection = new javax.swing.JButton();
        pnlDetail = new javax.swing.JPanel();
        pnlAvatar = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        btnNew = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnPrevious = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtFullname = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        txtConfirmPassword = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        chkManager = new javax.swing.JCheckBox();
        chkEmployee = new javax.swing.JCheckBox();
        chkInactive = new javax.swing.JCheckBox();
        chkActive = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý tài khoản");

        tblUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Tên đăng nhập", "Mật khẩu", "Họ và tên", "Hình ảnh", "Vai trò", "Trạng thái", "Select"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblUsers);

        btnSelectAll.setText("Chọn tất cả");
        btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectAllActionPerformed(evt);
            }
        });

        btnUnselectAll.setText("Bỏ chọn tất cả");
        btnUnselectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnselectAllActionPerformed(evt);
            }
        });

        btnClearSelection.setText("Xóa chọn tất cả");
        btnClearSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearSelectionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlListLayout = new javax.swing.GroupLayout(pnlList);
        pnlList.setLayout(pnlListLayout);
        pnlListLayout.setHorizontalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlListLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSelectAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUnselectAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClearSelection)
                .addContainerGap())
        );
        pnlListLayout.setVerticalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSelectAll)
                    .addComponent(btnUnselectAll)
                    .addComponent(btnClearSelection))
                .addContainerGap())
        );

        tabsUser.addTab("DANH SÁCH", pnlList);

        javax.swing.GroupLayout pnlAvatarLayout = new javax.swing.GroupLayout(pnlAvatar);
        pnlAvatar.setLayout(pnlAvatarLayout);
        pnlAvatarLayout.setHorizontalGroup(
            pnlAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 162, Short.MAX_VALUE)
        );
        pnlAvatarLayout.setVerticalGroup(
            pnlAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 209, Short.MAX_VALUE)
        );

        btnNew.setText("Tạo mới");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnUpdate.setText("Cập nhật");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnReset.setText("Nhập mới");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnFirst.setText("<<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnPrevious.setText("|<");
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Tên đăng nhập");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Xác nhận mật khẩu");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Họ và tên");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Mật khẩu");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Trạng thái");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Vai trò");

        chkManager.setText("Quản lý");
        chkManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkManagerActionPerformed(evt);
            }
        });

        chkEmployee.setText("Nhân viên");
        chkEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEmployeeActionPerformed(evt);
            }
        });

        chkInactive.setText("Tạm dừng");
        chkInactive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkInactiveActionPerformed(evt);
            }
        });

        chkActive.setText("Hoạt động");
        chkActive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkActiveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDetailLayout = new javax.swing.GroupLayout(pnlDetail);
        pnlDetail.setLayout(pnlDetailLayout);
        pnlDetailLayout.setHorizontalGroup(
            pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(pnlDetailLayout.createSequentialGroup()
                        .addComponent(pnlAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnlDetailLayout.createSequentialGroup()
                                    .addComponent(chkManager)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(chkEmployee))
                                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtFullname)
                                .addComponent(txtConfirmPassword)
                                .addGroup(pnlDetailLayout.createSequentialGroup()
                                    .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel2))
                                    .addGap(129, 129, 129)))
                            .addGroup(pnlDetailLayout.createSequentialGroup()
                                .addComponent(chkActive)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chkInactive))))
                    .addGroup(pnlDetailLayout.createSequentialGroup()
                        .addComponent(btnNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlDetailLayout.createSequentialGroup()
                    .addGap(184, 184, 184)
                    .addComponent(jLabel6)
                    .addContainerGap(470, Short.MAX_VALUE)))
        );
        pnlDetailLayout.setVerticalGroup(
            pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlDetailLayout.createSequentialGroup()
                        .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDetailLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pnlDetailLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(26, 26, 26)
                        .addComponent(jLabel5))
                    .addComponent(pnlAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(chkActive)
                        .addComponent(chkInactive))
                    .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(chkManager)
                        .addComponent(chkEmployee)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNew)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete)
                    .addComponent(btnReset)
                    .addComponent(btnLast)
                    .addComponent(btnFirst)
                    .addComponent(btnNext)
                    .addComponent(btnPrevious))
                .addContainerGap())
            .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDetailLayout.createSequentialGroup()
                    .addContainerGap(197, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addGap(104, 104, 104)))
        );

        tabsUser.addTab("Biểu mẫu", pnlDetail);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabsUser)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabsUser)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        moveNext();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        moveFirst();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectAllActionPerformed
        checkAll();
    }//GEN-LAST:event_btnSelectAllActionPerformed

    private void btnClearSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSelectionActionPerformed
        deleteCheckedItems();
    }//GEN-LAST:event_btnClearSelectionActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        create();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnUnselectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnselectAllActionPerformed
        uncheckAll();
    }//GEN-LAST:event_btnUnselectAllActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        clear();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        movePrevious();
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        moveLast();
    }//GEN-LAST:event_btnLastActionPerformed

    private void chkActiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkActiveActionPerformed
        if (chkInactive.isSelected()) {
            chkInactive.setSelected(false);
        }
    }//GEN-LAST:event_chkActiveActionPerformed

    private void chkInactiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkInactiveActionPerformed
        if (chkActive.isSelected()) {
            chkActive.setSelected(false);
        }
    }//GEN-LAST:event_chkInactiveActionPerformed

    private void chkEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEmployeeActionPerformed
        if (chkEmployee.isSelected()) {
            chkManager.setSelected(false);
        }
    }//GEN-LAST:event_chkEmployeeActionPerformed

    private void chkManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkManagerActionPerformed
        if (chkManager.isSelected()) {
            chkEmployee.setSelected(false); // Bỏ chọn "Nhân viên" nếu "Quản lý" được chọn
        }
    }//GEN-LAST:event_chkManagerActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserManagerJDialog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearSelection;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSelectAll;
    private javax.swing.JButton btnUnselectAll;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JCheckBox chkActive;
    private javax.swing.JCheckBox chkEmployee;
    private javax.swing.JCheckBox chkInactive;
    private javax.swing.JCheckBox chkManager;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel pnlAvatar;
    private javax.swing.JPanel pnlDetail;
    private javax.swing.JPanel pnlList;
    private javax.swing.JTabbedPane tabsUser;
    private javax.swing.JTable tblUsers;
    private javax.swing.JTextField txtConfirmPassword;
    private javax.swing.JTextField txtFullname;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
