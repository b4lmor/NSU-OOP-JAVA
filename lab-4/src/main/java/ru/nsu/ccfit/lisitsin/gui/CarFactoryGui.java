package ru.nsu.ccfit.lisitsin.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import ru.nsu.ccfit.lisitsin.factory.CarFactory;
import ru.nsu.ccfit.lisitsin.factory.dealer.Dealer;
import ru.nsu.ccfit.lisitsin.factory.worker.Worker;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.math.BigInteger;
import java.util.Locale;

public class CarFactoryGui extends JFrame {
    private JPanel carFactoryPanel;
    private JProgressBar accessoryProgressBar;
    private JProgressBar bodyProgressBar;
    private JProgressBar motorProgressBar;
    private JProgressBar carProgressBar;
    private JButton quitButton;
    private JTextField logoField;
    private JFormattedTextField accessoryStorageField;
    private JFormattedTextField bodyStorageField;
    private JFormattedTextField motorStorageField;
    private JFormattedTextField carStorageField;
    private JFormattedTextField workersField;
    private JFormattedTextField dealersField;
    private JFormattedTextField accessorySupplierField;
    private JFormattedTextField bodySupplierField;
    private JFormattedTextField motorSupplierField;
    private JFormattedTextField incomeField;

    private static boolean isFinished = true;

    public CarFactoryGui() {
        setContentPane(carFactoryPanel);
        setSize(500, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void createGui(CarFactory carFactory) {

        this.accessoryProgressBar.setMinimum(0);
        this.accessoryProgressBar.setMaximum(carFactory.getAccessoryStorage().getCapacity());
        this.bodyProgressBar.setMinimum(0);
        this.bodyProgressBar.setMaximum(carFactory.getBodyStorage().getCapacity());
        this.motorProgressBar.setMinimum(0);
        this.motorProgressBar.setMaximum(carFactory.getMotorStorage().getCapacity());
        this.carProgressBar.setMinimum(0);
        this.carProgressBar.setMaximum(carFactory.getCarStorage().getCapacity());

        this.quitButton.addActionListener(e -> {
            carFactory.stop();
            isFinished = false;
        });

        Thread updateThread = new Thread(() -> {
            while (true) {
                SwingUtilities.invokeLater(() -> {

                    this.accessoryStorageField.setValue(
                            String.format(
                                    "Accessory storage: [%d / %d]",
                                    carFactory.getAccessoryStorage().getCurrentSize(),
                                    carFactory.getAccessoryStorage().getCapacity()
                            )
                    );
                    this.motorStorageField.setValue(
                            String.format(
                                    "Motor storage: [%d / %d]",
                                    carFactory.getMotorStorage().getCurrentSize(),
                                    carFactory.getMotorStorage().getCapacity()
                            )
                    );
                    this.bodyStorageField.setValue(
                            String.format(
                                    "Body storage: [%d / %d]",
                                    carFactory.getBodyStorage().getCurrentSize(),
                                    carFactory.getBodyStorage().getCapacity()
                            )
                    );
                    this.carStorageField.setValue(
                            String.format(
                                    "Car storage: [%d / %d]",
                                    carFactory.getCarStorage().getCurrentSize(),
                                    carFactory.getCarStorage().getCapacity()
                            )
                    );

                    this.accessoryProgressBar.setValue(carFactory.getAccessoryStorage().getCurrentSize());
                    this.bodyProgressBar.setValue(carFactory.getBodyStorage().getCurrentSize());
                    this.motorProgressBar.setValue(carFactory.getMotorStorage().getCurrentSize());
                    this.carProgressBar.setValue(carFactory.getCarStorage().getCurrentSize());

                    this.workersField.setValue(
                            String.format(
                                    "Workers: [%d / %d]",
                                    carFactory.getWorkers().stream().filter(Worker::isNotAvailable).count(),
                                    carFactory.getWorkers().size()
                            )
                    );
                    this.dealersField.setValue(
                            String.format(
                                    "Dealers: [%d]",
                                    carFactory.getDealers().size()
                            )
                    );
                    this.accessorySupplierField.setValue(
                            String.format(
                                    "Accessory suppliers: [%d]",
                                    carFactory.getAccessorySuppliers().size()
                            )
                    );
                    this.bodySupplierField.setValue(
                            String.format(
                                    "Body suppliers: [%d]",
                                    carFactory.getBodySuppliers().size()
                            )
                    );
                    this.motorSupplierField.setValue(
                            String.format(
                                    "Motor suppliers: [%d]",
                                    carFactory.getMotorSuppliers().size()
                            )
                    );

                    this.incomeField.setValue(
                            String.format(
                                    "Total income: [%d$]",
                                    carFactory.getDealers()
                                            .stream()
                                            .map(Dealer::countTotalIncome)
                                            .reduce(BigInteger::add)
                                            .orElse(BigInteger.ZERO)
                            )
                    );

                });

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        updateThread.start();

    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        carFactoryPanel = new JPanel();
        carFactoryPanel.setLayout(new GridLayoutManager(20, 1, new Insets(0, 0, 0, 0), -1, -1));
        carFactoryPanel.setBackground(new Color(-30875));
        carFactoryPanel.setEnabled(true);
        Font carFactoryPanelFont = this.$$$getFont$$$("Consolas", Font.PLAIN, 20, carFactoryPanel.getFont());
        if (carFactoryPanelFont != null) carFactoryPanel.setFont(carFactoryPanelFont);
        carFactoryPanel.setForeground(new Color(-1));
        carFactoryPanel.setToolTipText("");
        accessoryStorageField = new JFormattedTextField();
        accessoryStorageField.setBackground(new Color(-343428));
        accessoryStorageField.setEditable(false);
        Font accessoryStorageFieldFont = this.$$$getFont$$$("Consolas", Font.PLAIN, 20, accessoryStorageField.getFont());
        if (accessoryStorageFieldFont != null) accessoryStorageField.setFont(accessoryStorageFieldFont);
        accessoryStorageField.setText("Accessory storage");
        carFactoryPanel.add(accessoryStorageField, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer1 = new Spacer();
        carFactoryPanel.add(spacer1, new GridConstraints(18, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        bodyStorageField = new JFormattedTextField();
        bodyStorageField.setBackground(new Color(-343428));
        bodyStorageField.setEditable(false);
        Font bodyStorageFieldFont = this.$$$getFont$$$("Consolas", Font.PLAIN, 20, bodyStorageField.getFont());
        if (bodyStorageFieldFont != null) bodyStorageField.setFont(bodyStorageFieldFont);
        bodyStorageField.setText("Body storage");
        carFactoryPanel.add(bodyStorageField, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        motorStorageField = new JFormattedTextField();
        motorStorageField.setBackground(new Color(-343428));
        motorStorageField.setEditable(false);
        Font motorStorageFieldFont = this.$$$getFont$$$("Consolas", Font.PLAIN, 20, motorStorageField.getFont());
        if (motorStorageFieldFont != null) motorStorageField.setFont(motorStorageFieldFont);
        motorStorageField.setText("Motor storage");
        carFactoryPanel.add(motorStorageField, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        carStorageField = new JFormattedTextField();
        carStorageField.setBackground(new Color(-343428));
        carStorageField.setEditable(false);
        Font carStorageFieldFont = this.$$$getFont$$$("Consolas", Font.PLAIN, 20, carStorageField.getFont());
        if (carStorageFieldFont != null) carStorageField.setFont(carStorageFieldFont);
        carStorageField.setText("Car storage");
        carFactoryPanel.add(carStorageField, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        workersField = new JFormattedTextField();
        workersField.setBackground(new Color(-343428));
        workersField.setEditable(false);
        Font workersFieldFont = this.$$$getFont$$$("Consolas", Font.PLAIN, 20, workersField.getFont());
        if (workersFieldFont != null) workersField.setFont(workersFieldFont);
        workersField.setText("Workers");
        carFactoryPanel.add(workersField, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        dealersField = new JFormattedTextField();
        dealersField.setBackground(new Color(-343428));
        dealersField.setEditable(false);
        Font dealersFieldFont = this.$$$getFont$$$("Consolas", Font.PLAIN, 20, dealersField.getFont());
        if (dealersFieldFont != null) dealersField.setFont(dealersFieldFont);
        dealersField.setText("Dealers");
        carFactoryPanel.add(dealersField, new GridConstraints(12, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        accessorySupplierField = new JFormattedTextField();
        accessorySupplierField.setBackground(new Color(-343428));
        accessorySupplierField.setEditable(false);
        Font accessorySupplierFieldFont = this.$$$getFont$$$("Consolas", Font.PLAIN, 20, accessorySupplierField.getFont());
        if (accessorySupplierFieldFont != null) accessorySupplierField.setFont(accessorySupplierFieldFont);
        accessorySupplierField.setText("Accessory supplier");
        carFactoryPanel.add(accessorySupplierField, new GridConstraints(13, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        bodySupplierField = new JFormattedTextField();
        bodySupplierField.setBackground(new Color(-343428));
        bodySupplierField.setEditable(false);
        Font bodySupplierFieldFont = this.$$$getFont$$$("Consolas", Font.PLAIN, 20, bodySupplierField.getFont());
        if (bodySupplierFieldFont != null) bodySupplierField.setFont(bodySupplierFieldFont);
        bodySupplierField.setText("Body supplier");
        carFactoryPanel.add(bodySupplierField, new GridConstraints(14, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        motorSupplierField = new JFormattedTextField();
        motorSupplierField.setBackground(new Color(-343428));
        motorSupplierField.setEditable(false);
        Font motorSupplierFieldFont = this.$$$getFont$$$("Consolas", Font.PLAIN, 20, motorSupplierField.getFont());
        if (motorSupplierFieldFont != null) motorSupplierField.setFont(motorSupplierFieldFont);
        motorSupplierField.setText("Motor supplier");
        carFactoryPanel.add(motorSupplierField, new GridConstraints(15, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        incomeField = new JFormattedTextField();
        incomeField.setBackground(new Color(-343428));
        incomeField.setEditable(false);
        Font incomeFieldFont = this.$$$getFont$$$("Consolas", Font.PLAIN, 20, incomeField.getFont());
        if (incomeFieldFont != null) incomeField.setFont(incomeFieldFont);
        incomeField.setText("Total income");
        carFactoryPanel.add(incomeField, new GridConstraints(17, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer2 = new Spacer();
        carFactoryPanel.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        carFactoryPanel.add(spacer3, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        accessoryProgressBar = new JProgressBar();
        carFactoryPanel.add(accessoryProgressBar, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bodyProgressBar = new JProgressBar();
        carFactoryPanel.add(bodyProgressBar, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        motorProgressBar = new JProgressBar();
        carFactoryPanel.add(motorProgressBar, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        carProgressBar = new JProgressBar();
        carFactoryPanel.add(carProgressBar, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        carFactoryPanel.add(panel1, new GridConstraints(19, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        quitButton = new JButton();
        quitButton.setBackground(new Color(-374216));
        Font quitButtonFont = this.$$$getFont$$$("Consolas", Font.BOLD, 20, quitButton.getFont());
        if (quitButtonFont != null) quitButton.setFont(quitButtonFont);
        quitButton.setForeground(new Color(-19677));
        quitButton.setHideActionText(false);
        quitButton.setText("Quit");
        panel1.add(quitButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel1.add(spacer4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel1.add(spacer5, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        carFactoryPanel.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        logoField = new JTextField();
        logoField.setEditable(false);
        Font logoFieldFont = this.$$$getFont$$$("Montserrat Black", Font.BOLD, 26, logoField.getFont());
        if (logoFieldFont != null) logoField.setFont(logoFieldFont);
        logoField.setHorizontalAlignment(0);
        logoField.setText("NSU MOTORS");
        panel2.add(logoField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer6 = new Spacer();
        carFactoryPanel.add(spacer6, new GridConstraints(16, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return carFactoryPanel;
    }

}
