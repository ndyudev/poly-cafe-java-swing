package poly.cafe.ui.manager;

import poly.cafe.dao.CategoryDAO;
import poly.cafe.dao.impl.CategoryDAOImpl;
import poly.cafe.entity.Category;
import poly.cafe.util.XDialog;

import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CategoryManagerJDialog extends javax.swing.JDialog implements CrudController<Category> {

    private CategoryDAO categoryDAO;
    private List<Category> categoryList;
    private int currentRow = -1;

    public CategoryManagerJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        categoryDAO = new CategoryDAOImpl();
        initComponents();
        init();
        open();
    }

    @Override
    public void open() {
        setTitle("Quản lý loại đồ uống");
        setLocationRelativeTo(null);
        fillToTable();
        setEditable(false);
        updateNavigationButtons();
    }

    @Override
    public void setForm(Category category) {
        txtId.setText(category.getId());
        txtName.setText(category.getName());
    }

    @Override
    public Category getForm() {
        if (!validateForm()) {
            return null;
        }

        String id = txtId.getText().trim();
        String name = txtName.getText().trim();

        return Category.builder()
                .id(id)
                .name(name)
                .build();
    }

    private boolean validateForm() {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            XDialog.alert("Vui lòng nhập đầy đủ mã loại và tên loại!");
            return false;
        }

        if (txtId.isEditable() && categoryList.stream().anyMatch(c -> c.getId().equals(id))) {
            XDialog.alert("Mã loại đã tồn tại!");
            return false;
        }

        return true;
    }

    @Override
    public void fillToTable() {
        categoryList = categoryDAO.findAll();
        DefaultTableModel model = (DefaultTableModel) tblCategories.getModel();
        model.setRowCount(0);

        for (Category category : categoryList) {
            model.addRow(new Object[]{
                category.getId(),
                category.getName(),
                false
            });
        }
        currentRow = categoryList.isEmpty() ? -1 : 0;
        if (currentRow >= 0) {
            setForm(categoryList.get(currentRow));
            tblCategories.setRowSelectionInterval(currentRow, currentRow);
        }
        updateNavigationButtons();
    }

    @Override
    public void edit() {
        int row = tblCategories.getSelectedRow();
        if (row >= 0) {
            currentRow = row;
            setForm(categoryList.get(row));
            setEditable(true);
            txtId.setEditable(false);
            Tabs.setSelectedIndex(1);
            updateNavigationButtons();
        }
    }

//    @Override
//    public void deleteAll() {
//        try {
//            XJdbc.executeUpdate(deleteAllSql);
//        } catch (Exception e) {
//            throw new RuntimeException("Lỗi khi xóa toàn bộ Product: " + e.getMessage(), e);
//        }
//    }
    @Override
    public void create() {
        Category category = getForm();
        if (category == null) {
            return;
        }

        try {
            categoryDAO.create(category);
            XDialog.alert("Tạo loại đồ uống thành công!");
            fillToTable();
            clear();
        } catch (Exception e) {
            XDialog.alert("Lỗi khi tạo loại đồ uống: " + e.getMessage());
        }
    }

    @Override
    public void update() {
        Category category = getForm();
        if (category == null) {
            return;
        }

        try {
            categoryDAO.update(category);
            XDialog.alert("Cập nhật loại đồ uống thành công!");
            fillToTable();
            clear();
        } catch (Exception e) {
            XDialog.alert("Lỗi khi cập nhật loại đồ uống: " + e.getMessage());
        }
    }

    @Override
    public void delete() {
        if (currentRow < 0) {
            XDialog.alert("Vui lòng chọn loại đồ uống để xóa!");
            return;
        }
        if (XDialog.confirm("Bạn có chắc muốn xóa loại đồ uống này?")) {
            try {
                categoryDAO.deleteById(categoryList.get(currentRow).getId());
                XDialog.alert("Xóa loại đồ uống thành công!");
                fillToTable();
                clear();
            } catch (Exception e) {
                XDialog.alert("Lỗi khi xóa loại đồ uống: " + e.getMessage());
            }
        }
    }

    @Override
    public void clear() {
        txtId.setText("");
        txtName.setText("");
        setEditable(true);
        txtId.setEditable(true);
        currentRow = -1;
        updateNavigationButtons();
    }

    @Override
    public void setEditable(boolean editable) {
        txtId.setEditable(editable);
        txtName.setEditable(editable);
        btnCreate.setEnabled(editable);
        btnUpdate.setEnabled(editable && currentRow >= 0);
        btnDelete.setEnabled(editable && currentRow >= 0);
    }

    @Override
    public void checkAll() {
        for (int i = 0; i < tblCategories.getRowCount(); i++) {
            tblCategories.setValueAt(true, i, 2);
        }
    }

    @Override
    public void uncheckAll() {
        for (int i = 0; i < tblCategories.getRowCount(); i++) {
            tblCategories.setValueAt(false, i, 2);
        }
    }

    @Override
    public void deleteCheckedItems() {
        if (XDialog.confirm("Bạn có chắc muốn xóa các loại đồ uống được chọn?")) {
            try {
                boolean hasDeletion = false;
                for (int i = 0; i < tblCategories.getRowCount(); i++) {
                    if ((Boolean) tblCategories.getValueAt(i, 2)) {
                        categoryDAO.deleteById(categoryList.get(i).getId());
                        hasDeletion = true;
                    }
                }
                if (hasDeletion) {
                    XDialog.alert("Xóa các loại đồ uống được chọn thành công!");
                    fillToTable();
                    clear();
                }
            } catch (Exception e) {
                XDialog.alert("Lỗi khi xóa các loại đồ uống: " + e.getMessage());
            }
        }
    }

    @Override
    public void moveFirst() {
        if (!categoryList.isEmpty()) {
            currentRow = 0;
            moveTo(currentRow);
        }
    }

    @Override
    public void movePrevious() {
        if (currentRow > 0) {
            currentRow--;
            moveTo(currentRow);
        }
    }

    @Override
    public void moveNext() {
        if (currentRow < categoryList.size() - 1) {
            currentRow++;
            moveTo(currentRow);
        }
    }

    @Override
    public void moveLast() {
        if (!categoryList.isEmpty()) {
            currentRow = categoryList.size() - 1;
            moveTo(currentRow);
        }
    }

    @Override
    public void moveTo(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < categoryList.size()) {
            currentRow = rowIndex;
            setForm(categoryList.get(rowIndex));
            tblCategories.setRowSelectionInterval(rowIndex, rowIndex);
            Tabs.setSelectedIndex(1);
            setEditable(true);
            txtId.setEditable(false);
            updateNavigationButtons();
        }
    }

    private void updateNavigationButtons() {
        btnMoveFirst.setEnabled(currentRow > 0);
        btnMovePrevious.setEnabled(currentRow > 0);
        btnMoveNext.setEnabled(currentRow < categoryList.size() - 1);
        btnMoveLast.setEnabled(currentRow < categoryList.size() - 1);
    }

    private void init() {
        // Thêm sự kiện nhấp đúp vào bảng để chỉnh sửa
        tblCategories.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    edit();
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCategories = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        btnCheckAll = new javax.swing.JButton();
        btnUncheckAll = new javax.swing.JButton();
        btnDeleteCheckedItems = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btnCreate = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnMoveLast = new javax.swing.JButton();
        btnMoveNext = new javax.swing.JButton();
        btnMovePrevious = new javax.swing.JButton();
        btnMoveFirst = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Quản lý loại đồ uống");

        tblCategories.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã loại", "Tên loại", "Select"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblCategories);

        btnCheckAll.setText("Chọn tất cả");
        btnCheckAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckAllActionPerformed(evt);
            }
        });

        btnUncheckAll.setText("Bỏ chọn tất cả");
        btnUncheckAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUncheckAllActionPerformed(evt);
            }
        });

        btnDeleteCheckedItems.setText("Xóa các mục chọn");
        btnDeleteCheckedItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCheckedItemsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCheckAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUncheckAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteCheckedItems)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeleteCheckedItems)
                    .addComponent(btnUncheckAll)
                    .addComponent(btnCheckAll))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Tabs.addTab("DANH SÁCH", jPanel1);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Mã loại");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Mã loại");

        btnCreate.setText("Tạo mới");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
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

        btnClear.setText("Nhập mới");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnMoveLast.setText(">|");
        btnMoveLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveLastActionPerformed(evt);
            }
        });

        btnMoveNext.setText(">>");
        btnMoveNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveNextActionPerformed(evt);
            }
        });

        btnMovePrevious.setText("<<");
        btnMovePrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMovePreviousActionPerformed(evt);
            }
        });

        btnMoveFirst.setText("|<");
        btnMoveFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveFirstActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnCreate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 155, Short.MAX_VALUE)
                        .addComponent(btnMoveFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMovePrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMoveNext, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMoveLast, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMoveLast)
                    .addComponent(btnMoveNext)
                    .addComponent(btnMovePrevious)
                    .addComponent(btnCreate)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete)
                    .addComponent(btnClear)
                    .addComponent(btnMoveFirst))
                .addContainerGap())
        );

        Tabs.addTab("BIỂU MẪU", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Tabs)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMoveFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveFirstActionPerformed
        // TODO add your handling code here:
        moveFirst();
    }//GEN-LAST:event_btnMoveFirstActionPerformed

    private void btnCheckAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckAllActionPerformed
        // TODO add your handling code here:
        checkAll();
    }//GEN-LAST:event_btnCheckAllActionPerformed

    private void btnUncheckAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUncheckAllActionPerformed
        // TODO add your handling code here:
        uncheckAll();
    }//GEN-LAST:event_btnUncheckAllActionPerformed

    private void btnDeleteCheckedItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCheckedItemsActionPerformed

        if (XDialog.confirm("Bạn có chắc muốn xóa toàn bộ danh mục? Lưu ý: Tất cả đồ uống liên quan cũng sẽ bị xóa!")) {
            try {
                CategoryDAO categoryDAO = new CategoryDAOImpl();
                categoryDAO.deleteAll();
                fillToTable(); // Cập nhật bảng
                clear(); // Xóa form
                XDialog.alert("Xóa toàn bộ danh mục thành công!");
            } catch (Exception e) {
                XDialog.alert("Lỗi khi xóa: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnDeleteCheckedItemsActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        create();
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnMovePreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMovePreviousActionPerformed
        // TODO add your handling code here:
        movePrevious();
    }//GEN-LAST:event_btnMovePreviousActionPerformed

    private void btnMoveNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveNextActionPerformed
        // TODO add your handling code here:
        moveNext();
    }//GEN-LAST:event_btnMoveNextActionPerformed

    private void btnMoveLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveLastActionPerformed
        // TODO add your handling code here:
        moveLast();
    }//GEN-LAST:event_btnMoveLastActionPerformed

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
            java.util.logging.Logger.getLogger(CategoryManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CategoryManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CategoryManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CategoryManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CategoryManagerJDialog dialog = new CategoryManagerJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Tabs;
    private javax.swing.JButton btnCheckAll;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeleteCheckedItems;
    private javax.swing.JButton btnMoveFirst;
    private javax.swing.JButton btnMoveLast;
    private javax.swing.JButton btnMoveNext;
    private javax.swing.JButton btnMovePrevious;
    private javax.swing.JButton btnUncheckAll;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable tblCategories;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
