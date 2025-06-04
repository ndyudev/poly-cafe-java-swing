package poly.cafe.ui.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import poly.cafe.dao.RevenueDAO;
import poly.cafe.dao.impl.RevenueDAOImpl;
import poly.cafe.entity.Revenue;
import poly.cafe.util.TimeRange;
import poly.cafe.util.XDate;

public class RevenueManagerJDialog extends javax.swing.JDialog implements RevenueController, CrudController<Revenue> {

    private Revenue currentRevenue; // Thực thể hiện tại (chỉ dùng để tuân thủ interface)
    private int currentRowIndex = -1; // Vị trí hàng hiện tại
    private List<Revenue.ByCategory> categoryData = new ArrayList<>(); // Lưu dữ liệu ByCategory
    private List<Revenue.ByUser> userData = new ArrayList<>(); // Lưu dữ liệu ByUser
    private final RevenueDAO dao = new RevenueDAOImpl(); // Khởi tạo một lần

    public RevenueManagerJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        open();
    }

    @Override
    public void open() {
        this.setLocationRelativeTo(null);
        // Gán sự kiện thay đổi thời gian từ TimeFilterJPanel
        filter.setTimeChanged((begin, end) -> fillRevenue());
        selectTimeRange(); // Khởi tạo dữ liệu mặc định
    }

    @Override
    public void selectTimeRange() {
        TimeRange range = TimeRange.today(); // Mặc định là hôm nay
        switch (filter.getSelectedIndex()) {
            case 0 ->
                range = TimeRange.today();
            case 1 ->
                range = TimeRange.thisWeek();
            case 2 ->
                range = TimeRange.thisMonth();
            case 3 ->
                range = TimeRange.thisQuarter();
            case 4 ->
                range = TimeRange.thisYear();
        }
        filter.setFrom(range.getBegin());
        filter.setTo(range.getEnd());
        fillRevenue(); // Cập nhật dữ liệu khi chọn khoảng thời gian
    }

    @Override
    public void fillRevenue() {
        Date begin = filter.getFrom();
        Date end = filter.getTo();
        if (begin == null || end == null || begin.after(end)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu hoặc kết thúc không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Lấy dữ liệu theo tab đang chọn
        switch (tabs.getSelectedIndex()) {
            case 0 ->
                fillRevenueByCategory(begin, end);
            case 1 ->
                fillRevenueByUser(begin, end);
            default -> {
                fillRevenueByCategory(begin, end); // Mặc định là tab ByCategory
                fillRevenueByUser(begin, end); // Đổ cả hai để đảm bảo dữ liệu sẵn sàng
            }
        }
    }

    private void fillRevenueByCategory(Date begin, Date end) {
        categoryData = dao.getByCategory(begin, end);
        DefaultTableModel model = (DefaultTableModel) tblByCategory.getModel();
        model.setRowCount(0);
        categoryData.forEach(item -> {
            Object[] row = {
                item.getCategory(),
                String.format("$%.2f", item.getRevenue()),
                item.getQuantity(),
                String.format("$%.2f", item.getMinPrice()),
                String.format("$%.2f", item.getMaxPrice()),
                String.format("$%.2f", item.getAvgPrice())
            };
            model.addRow(row);
        });
    }

    private void fillRevenueByUser(Date begin, Date end) {
        userData = dao.getByUser(begin, end);
        DefaultTableModel model = (DefaultTableModel) tblByUser.getModel();
        model.setRowCount(0);
        userData.forEach(item -> {
            Object[] row = {
                item.getUser(),
                String.format("$%.2f", item.getRevenue()),
                item.getQuantity(),
                XDate.format(item.getFirstTime(), "hh:mm:ss dd-MM-yyyy"),
                XDate.format(item.getLastTime(), "hh:mm:ss dd-MM-yyyy")
            };
            model.addRow(row);
        });
    }

    @Override
    public void setForm(Revenue entity) {
        currentRevenue = entity; // Không sử dụng trực tiếp, chỉ tuân thủ interface
        fillToTable(); // Cập nhật bảng (dù không cần thiết ở đây)
    }

    @Override
    public Revenue getForm() {
        return currentRevenue; // Trả về null hoặc giá trị mặc định
    }

    @Override
    public void fillToTable() {
        // Không cần gọi fillRevenue() nữa vì đã xử lý trong fillRevenue()
        // Phương thức này có thể để trống hoặc xử lý giao diện bổ sung nếu cần
    }

    @Override
    public void edit() {
        int row = tabs.getSelectedIndex() == 0 ? tblByCategory.getSelectedRow() : tblByUser.getSelectedRow();
        if (row >= 0) {
            currentRowIndex = row;
            JOptionPane.showMessageDialog(this, "Đã chọn hàng: " + row, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void create() {
        JOptionPane.showMessageDialog(this, "Không thể tạo mới báo cáo doanh thu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void update() {
        JOptionPane.showMessageDialog(this, "Không thể cập nhật báo cáo doanh thu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void delete() {
        JOptionPane.showMessageDialog(this, "Không thể xóa báo cáo doanh thu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void clear() {
        ((DefaultTableModel) tblByCategory.getModel()).setRowCount(0);
        ((DefaultTableModel) tblByUser.getModel()).setRowCount(0);
        categoryData.clear();
        userData.clear();
        currentRevenue = null;
        currentRowIndex = -1;
    }

    @Override
    public void setEditable(boolean editable) {
        filter.setEnabled(editable);
        tblByCategory.setEnabled(editable);
        tblByUser.setEnabled(editable);
    }

    @Override
    public void checkAll() {
        DefaultTableModel model = (DefaultTableModel) (tabs.getSelectedIndex() == 0 ? tblByCategory.getModel() : tblByUser.getModel());
        for (int i = 0; i < model.getRowCount(); i++) {
            System.out.println("Tích chọn hàng: " + i);
        }
        JOptionPane.showMessageDialog(this, "Đã tích chọn tất cả (chưa hỗ trợ checkbox)!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void uncheckAll() {
        DefaultTableModel model = (DefaultTableModel) (tabs.getSelectedIndex() == 0 ? tblByCategory.getModel() : tblByUser.getModel());
        for (int i = 0; i < model.getRowCount(); i++) {
            System.out.println("Bỏ tích chọn hàng: " + i);
        }
        JOptionPane.showMessageDialog(this, "Đã bỏ tích chọn tất cả (chưa hỗ trợ checkbox)!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void deleteCheckedItems() {
        JOptionPane.showMessageDialog(this, "Không thể xóa báo cáo doanh thu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void moveFirst() {
        moveTo(0);
    }

    @Override
    public void movePrevious() {
        if (currentRowIndex > 0) {
            moveTo(currentRowIndex - 1);
        }
    }

    @Override
    public void moveNext() {
        DefaultTableModel model = (DefaultTableModel) (tabs.getSelectedIndex() == 0 ? tblByCategory.getModel() : tblByUser.getModel());
        if (currentRowIndex < model.getRowCount() - 1) {
            moveTo(currentRowIndex + 1);
        }
    }

    @Override
    public void moveLast() {
        DefaultTableModel model = (DefaultTableModel) (tabs.getSelectedIndex() == 0 ? tblByCategory.getModel() : tblByUser.getModel());
        moveTo(model.getRowCount() - 1);
    }

    @Override
    public void moveTo(int rowIndex) {
        DefaultTableModel model = (DefaultTableModel) (tabs.getSelectedIndex() == 0 ? tblByCategory.getModel() : tblByUser.getModel());
        if (rowIndex >= 0 && rowIndex < model.getRowCount()) {
            currentRowIndex = rowIndex;
            edit();
            (tabs.getSelectedIndex() == 0 ? tblByCategory : tblByUser).setRowSelectionInterval(rowIndex, rowIndex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filter = new poly.cafe.ui.component.TimeFilterJPanel();
        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblByCategory = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblByUser = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        filter.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                filterComponentAdded(evt);
            }
        });

        tblByCategory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Loại", "Doanh thu", "Số lượng", "Giá thấp nhất", "Giá cao nhất", "Giá trung bình"
            }
        ));
        tblByCategory.setSelectionBackground(new java.awt.Color(255, 255, 0));
        tblByCategory.setSelectionForeground(new java.awt.Color(255, 0, 0));
        jScrollPane1.setViewportView(tblByCategory);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
        );

        tabs.addTab("Doanh thu từng loại", jPanel1);

        tblByUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nhân viên", "Doanh thu", "Số bill", "Bill đầu tiên", "Bill cuối cùng"
            }
        ));
        tblByUser.setSelectionBackground(new java.awt.Color(255, 255, 0));
        tblByUser.setSelectionForeground(new java.awt.Color(255, 0, 0));
        jScrollPane2.setViewportView(tblByUser);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
        );

        tabs.addTab("Doanh thu từng nhân viên", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
            .addComponent(filter, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filter, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void filterComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_filterComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_filterComponentAdded

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
            java.util.logging.Logger.getLogger(RevenueManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RevenueManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RevenueManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RevenueManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RevenueManagerJDialog dialog = new RevenueManagerJDialog(new javax.swing.JFrame(), true);
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
    private poly.cafe.ui.component.TimeFilterJPanel filter;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblByCategory;
    private javax.swing.JTable tblByUser;
    // End of variables declaration//GEN-END:variables
}
