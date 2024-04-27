import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import com.toedter.calendar.JDateChooser;
public abstract class Menu  {

    private String title;

    public Menu(String title) {
        this.title = title;
    }

    abstract public void menu();

    public void msg(String msg) {
        JOptionPane.showMessageDialog(null, msg, "title", 1);
    }

    public String input(String msg) {
        return JOptionPane.showInputDialog(null, msg, title, 3) ;
    }

    public void msgScroll(String msg) {
        JTextArea textArea = new JTextArea(msg);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize( new Dimension( 500, 500 ) );
        JOptionPane.showMessageDialog(null, scrollPane, title, 1);
    }

    public Date inputDate() {

        // Crear un nuevo marco (ventana)
        JFrame frame = new JFrame("Seleccionar fecha");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Configurar el cierre del programa cuando se cierra la ventana
        frame.setSize(300, 200); // Establecer el tamaño del marco

        // Crear un nuevo panel principal con disposición GridBagLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        frame.add(mainPanel);

        // Configurar restricciones para los componentes dentro del panel
        GridBagConstraints constraints = new GridBagConstraints();

        // Crear y agregar una etiqueta al panel
        JLabel label = new JLabel("Seleccione una fecha:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(label, constraints);

        // Crear y agregar un JDateChooser (selector de fecha) al panel
        JDateChooser fecha = new JDateChooser();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(fecha, constraints);

        // Crear y agregar un botón al panel
        JButton button = new JButton("Guardar fecha");
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(button, constraints);

        // Agregar un listener al botón para manejar eventos de clic
        AtomicReference<Date> FinalDate = new AtomicReference<>();
        button.addActionListener(e -> {
            Date fechaselect = fecha.getDate(); // Obtener la fecha seleccionada del JDateChooser
            if (fechaselect != null) {
                System.out.println("Fecha seleccionada!");
                FinalDate.set(fechaselect);
                frame.dispose();
            } else {
                // Imprimir si no se seleccionó ninguna fecha
                System.out.println("No se seleccionó ninguna fecha");
            }
        });

        // Hacer visible el marco
        frame.setVisible(true);

        // Esperar que se capture la fecha para retornarla
        while (FinalDate.get() == null) {
            // Esperar mas tiempo entre verificacion para reducir el uso de CPU
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return FinalDate.get();
    }
}
