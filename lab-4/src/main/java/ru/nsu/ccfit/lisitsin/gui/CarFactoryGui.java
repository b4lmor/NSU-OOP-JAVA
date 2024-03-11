package ru.nsu.ccfit.lisitsin.gui;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.lisitsin.factory.CarFactory;
import ru.nsu.ccfit.lisitsin.factory.dealer.Dealer;
import ru.nsu.ccfit.lisitsin.factory.worker.Worker;

import javax.swing.*;
import java.math.BigInteger;
import java.util.List;

@Log4j2
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
    private JSlider motorSlider;
    private JSlider bodySlider;
    private JSlider accessorySlider;
    private JSlider dealerSlider;
    private JSlider workerSlider;

    private static boolean isStarted = true;

    public CarFactoryGui() {
        setContentPane(carFactoryPanel);
        setSize(500, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @SneakyThrows
    public void createGui(CarFactory carFactory) {

        this.accessoryProgressBar.setMinimum(0);
        this.accessoryProgressBar.setMaximum(carFactory.getAccessoryStorage().getCapacity());
        this.bodyProgressBar.setMinimum(0);
        this.bodyProgressBar.setMaximum(carFactory.getBodyStorage().getCapacity());
        this.motorProgressBar.setMinimum(0);
        this.motorProgressBar.setMaximum(carFactory.getMotorStorage().getCapacity());
        this.carProgressBar.setMinimum(0);
        this.carProgressBar.setMaximum(carFactory.getCarStorage().getCapacity());

        List.of(
                accessorySlider,
                bodySlider,
                motorSlider,
                workerSlider,
                dealerSlider
        ).forEach(slider -> {
            slider.setMaximum(20000);
            slider.setMinimum(1000);
            slider.setMajorTickSpacing(1000);
            slider.setMinorTickSpacing(500);
            slider.setValue(5000);
        });

        this.quitButton.addActionListener(e -> {
            isStarted = false;
            log.info("[CAR-FACTORY-GUI] :: Closing ...");
            dispose();
            log.info("[CAR-FACTORY-GUI] :: Done.");
            System.exit(0);
        });

        Thread updateThread = new Thread(() -> {
            while (isStarted) {
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

                    carFactory.getAccessorySuppliers().forEach(s -> s.setDelay(this.accessorySlider.getValue()));
                    carFactory.getBodySuppliers().forEach(s -> s.setDelay(this.bodySlider.getValue()));
                    carFactory.getMotorSuppliers().forEach(s -> s.setDelay(this.motorSlider.getValue()));
                    carFactory.getWorkers().forEach(s -> s.setDelay(this.workerSlider.getValue()));
                    carFactory.getDealers().forEach(s -> s.setDelay(this.dealerSlider.getValue()));

                });

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

        updateThread.start();

        //updateThread.join();
        //carFactory.stop();
        //dispose();
    }
    
}
