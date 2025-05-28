package poly.cafe.ui;

import javax.swing.JFrame;
import poly.cafe.dao.UserDAO;
import poly.cafe.dao.impl.UserDAOImpl;
import poly.cafe.entity.User;
import poly.cafe.ui.manager.PolyCafeJFrameManager;
import poly.cafe.util.XAuth;
import poly.cafe.util.XDialog;

public class LoginJDialog extends javax.swing.JFrame implements LoginController {

    private JFrame parent;

    public LoginJDialog(JFrame parent, boolean modal) {
        this.parent = parent;
        initComponents();
        setLocationRelativeTo(parent);
    }

    private void showPolyCafeJFrameManager() {
        try {
            PolyCafeJFrameManager frame = new PolyCafeJFrameManager(parent);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            this.dispose();
        } catch (Exception e) {
            System.err.println("Error in showPolyCafeJFrameManager: " + e.getMessage());
            XDialog.alert("Lỗi khi mở giao diện quản lý: " + e.getMessage());
        }
    }

    private void showPolyCafeJFrame() {
        try {
            SalesJDialog frameUI = new SalesJDialog(parent, true);
            frameUI.setVisible(true);
            frameUI.setLocationRelativeTo(null);
            this.dispose();
        } catch (Exception e) {
            System.err.println("Error in showPolyCafeJFrame: " + e.getMessage());
            XDialog.alert("Lỗi khi mở giao diện bán hàng: " + e.getMessage());
        }
    }

    @Override
    public void login() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            XDialog.alert("Tên đăng nhập hoặc mật khẩu không được để trống!");
            return;
        }

        UserDAO dao = new UserDAOImpl();
        User user = dao.findById(username);
        System.out.println("Debug: Username=" + username + ", User found=" + (user != null));
        if (user == null) {
//            XDialog.alert("Sai tên đăng nhập!");
        } else if (!password.equals(user.getPassword())) {
//            XDialog.alert("Sai mật khẩu đăng nhập!");
        } else if (!user.isEnabled()) {
//            XDialog.alert("Tài khoản của bạn đang tạm dừng!");
        } else {
            XAuth.user = user;
            System.out.println("Debug: Login successful, isManager=" + user.isManager());
            if (user.isManager()) {
                showPolyCafeJFrameManager();
            } else {
                showPolyCafeJFrame();
            }
        }
    }

    @Override
    public void open() {
        this.setLocationRelativeTo(null);
    }

    @Override
    public void exit() {
        if (XDialog.confirm("Bạn muốn kết thúc?")) {
            System.exit(0);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        btnLogin = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login Poly Cafe");

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Chau Nhat Duy\\Documents\\Java-Swing-Project\\PS44284_ChauNhatDuy_SOF2043\\PS44284_ChauNhatDuy_SOF2043\\src\\main\\java\\poly\\cafe\\icons\\trump-small.png"));
        jLabel1.setText("jLabel1");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36));
        jLabel2.setText("Đăng Nhập");

        jSeparator1.setAlignmentX(2.0F);
        jSeparator1.setAlignmentY(3.0F);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 2, 18));
        jLabel3.setText("Tên đăng nhập:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 2, 18));
        jLabel4.setText("Mật khẩu:");

        btnLogin.setText("ĐĂNG NHẬP");
        btnLogin.addActionListener(evt -> login());

        btnClose.setText("KẾT THÚC");
        btnClose.addActionListener(evt -> exit());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator2)
                    .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addComponent(txtPassword))
                .addContainerGap(46, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogin)
                .addGap(18, 18, 18)
                .addComponent(btnClose)
                .addGap(62, 62, 62))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin)
                    .addComponent(btnClose))
                .addGap(60, 60, 60))
        );

        pack();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            JFrame hiddenFrame = new JFrame();
            hiddenFrame.setUndecorated(true);
            hiddenFrame.setSize(0, 0);
            hiddenFrame.setVisible(true);
            LoginJDialog dialog = new LoginJDialog(hiddenFrame, true);
            dialog.setVisible(true);
        });
    }

    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
}