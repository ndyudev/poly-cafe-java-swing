
package poly.cafe.ui.component;

import java.util.function.Consumer;
import lombok.Setter;
import poly.cafe.entity.Drink;
import poly.cafe.util.XIcon;


public class DrinkJPanel extends javax.swing.JPanel {


    public DrinkJPanel() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblImage = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        lblName = new javax.swing.JLabel();
        lblUnitPrice = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 102, 102), 1, true));
        setLayout(new java.awt.BorderLayout(5, 5));

        lblImage.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        lblImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImage.setText("Image");
        lblImage.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 1, 10, 1));
        lblImage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImageMouseClicked(evt);
            }
        });
        add(lblImage, java.awt.BorderLayout.CENTER);

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout(5, 5));

        jSeparator1.setForeground(new java.awt.Color(255, 102, 102));
        jPanel1.add(jSeparator1, java.awt.BorderLayout.PAGE_START);

        lblName.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblName.setText("Name");
        jPanel1.add(lblName, java.awt.BorderLayout.LINE_START);

        lblUnitPrice.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblUnitPrice.setText("Price");
        jPanel1.add(lblUnitPrice, java.awt.BorderLayout.LINE_END);

        add(jPanel1, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void lblImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImageMouseClicked
        // TODO add your handling code here:
        if(imageClicked != null){
            this.imageClicked.accept(drink);
        }
    }//GEN-LAST:event_lblImageMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblUnitPrice;
    // End of variables declaration//GEN-END:variables

    private Drink drink;
    @Setter
    int width = 100;
    @Setter
    int height = 120;
    
    @Setter
    Consumer<Drink> imageClicked;

    public void setDrink(Drink drink) {
        this.drink = drink;
        lblName.setText(drink.getName());
        lblUnitPrice.setText(String.format("%.1f", drink.getUnitPrice()*(1-drink.getDiscount())));
        lblImage.setText("");
        lblImage.setIcon(XIcon.getIcon("images/" + drink.getImage(), width, height));
    }
    
    public Drink getDrink(){
        return this.drink;
    }
}
