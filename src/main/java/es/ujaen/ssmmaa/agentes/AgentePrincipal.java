package es.ujaen.ssmmaa.agentes;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.MicroRuntime;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Agente principal que inicia un agente secundario para realizar operaciones matemáticas.
 */
public class AgentePrincipal extends Agent {

    private AgentePrincipalGUI gui;
    // Variable para el área de resultados
    private JTextArea txtAreaResultados;


    @Override
    protected void setup() {
        System.out.println("Iniciando Agente Principal: " + getLocalName());

        // CREACIÓN DE LA INTERFAZ GRÁFICA
        crearGUI();


        // COMPORTAMIENTO CÍCLICO PARA RECIBIR MENSAJES
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage mensaje = receive();
                if (mensaje != null) {
                    System.out.println("Mensaje recibido: " + mensaje.getContent());
                    actualizarInterfaz(mensaje.getContent());
                } else {
                    block();
                }
            }
        });
    }

    // Método para inicializar la interfaz gráfica
    private void crearGUI() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Agente Principal");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setLayout(new BorderLayout());

            // 🟢 MENÚ SUPERIOR
            JMenuBar menuBar = new JMenuBar();

            // 🟢 Menú de Operaciones
            JMenu menuOperaciones = new JMenu("Operaciones");
            JRadioButtonMenuItem sumaItem = new JRadioButtonMenuItem("Suma", true);
            JRadioButtonMenuItem restaItem = new JRadioButtonMenuItem("Resta");
            JRadioButtonMenuItem multiplicacionItem = new JRadioButtonMenuItem("Multiplicación");

            ButtonGroup operacionGroup = new ButtonGroup();
            operacionGroup.add(sumaItem);
            operacionGroup.add(restaItem);
            operacionGroup.add(multiplicacionItem);

            menuOperaciones.add(sumaItem);
            menuOperaciones.add(restaItem);
            menuOperaciones.add(multiplicacionItem);
            menuBar.add(menuOperaciones);

            // 🟢 Menú de Comportamientos
            JMenu menuComportamientos = new JMenu("Comportamientos");
            JRadioButtonMenuItem oneShotItem = new JRadioButtonMenuItem("OneShot", true);
            JRadioButtonMenuItem cyclicItem = new JRadioButtonMenuItem("Cyclic");

            ButtonGroup comportamientoGroup = new ButtonGroup();
            comportamientoGroup.add(oneShotItem);
            comportamientoGroup.add(cyclicItem);

            menuComportamientos.add(oneShotItem);
            menuComportamientos.add(cyclicItem);
            menuBar.add(menuComportamientos);

            // 🟢 Submenú de ciclos
            JMenu submenuCiclos = new JMenu("Número de ciclos");
            ButtonGroup cicloGroup = new ButtonGroup();
            JRadioButtonMenuItem[] cicloItems = new JRadioButtonMenuItem[5];

            for (int i = 0; i < 5; i++) {
                int ciclos = i + 2;
                cicloItems[i] = new JRadioButtonMenuItem(ciclos + " ciclos");
                cicloGroup.add(cicloItems[i]);
                submenuCiclos.add(cicloItems[i]);
            }

            // ✅ Añadimos el submenú de ciclos dentro del menú de comportamientos
            menuComportamientos.add(submenuCiclos);

            frame.setJMenuBar(menuBar);

            // 🟢 PANEL CENTRAL
            JPanel panel = new JPanel(new GridLayout(3, 2));
            JTextField num1Field = new JTextField(5);
            JTextField num2Field = new JTextField(5);
            JButton ejecutarButton = new JButton("Ejecutar");

            panel.add(new JLabel("Número 1:"));
            panel.add(num1Field);
            panel.add(new JLabel("Número 2:"));
            panel.add(num2Field);
            panel.add(ejecutarButton);

            frame.add(panel, BorderLayout.CENTER);

            // 🟢 ÁREA DE RESULTADOS
            txtAreaResultados = new JTextArea(5, 30);
            txtAreaResultados.setEditable(false);
            frame.add(new JScrollPane(txtAreaResultados), BorderLayout.SOUTH);

            // ✅ Acción del botón Ejecutar
            ejecutarButton.addActionListener(e -> {
                // Obtener operación seleccionada
                String operacionSeleccionada = "Suma"; // Por defecto
                if (restaItem.isSelected()) operacionSeleccionada = "Resta";
                if (multiplicacionItem.isSelected()) operacionSeleccionada = "Multiplicación";

                // Obtener comportamiento seleccionado
                String comportamientoSeleccionado = oneShotItem.isSelected() ? "OneShot" : "Cyclic";
                int ciclosSeleccionados = 2; // 🔹 Valor predeterminado para evitar 0 ciclos

                if (cyclicItem.isSelected()) {
                    for (JRadioButtonMenuItem item : cicloItems) {
                        if (item.isSelected()) {
                            ciclosSeleccionados = Integer.parseInt(item.getText().split(" ")[0]);
                            break;
                        }
                    }
                }

                // Enviar la operación seleccionada correctamente
                lanzarAgenteSecundario(num1Field.getText(), num2Field.getText(), operacionSeleccionada, comportamientoSeleccionado, ciclosSeleccionados);
            });

            frame.setVisible(true);
        });
    }


    // Método para actualizar la interfaz gráfica con el resultado
    private void actualizarInterfaz(String resultado) {
        SwingUtilities.invokeLater(() -> {
            txtAreaResultados.append(resultado + "\n"); // Agregar el nuevo resultado en una nueva línea
        });
    }

    // Método para lanzar el AgenteSecundario
    private void lanzarAgenteSecundario(String n1, String n2, String operacion, String comportamiento, int ciclos) {
        try {
            int num1 = Integer.parseInt(n1);
            int num2 = Integer.parseInt(n2);
            Object[] args = {num1, num2, operacion, comportamiento, ciclos,getLocalName()};

            // Crear el agente secundario
            MicroRuntime.startAgent("AgenteSecundario", "es.ujaen.ssmmaa.agentes.AgenteSecundario", args);
            System.out.println("✅ AgenteSecundario creado con éxito.");
        } catch (NumberFormatException ex) {
            System.err.println("❌ Error: Introduce números válidos.");
        } catch (Exception ex) {
            System.err.println("❌ Error al crear AgenteSecundario: " + ex.getMessage());
            ex.printStackTrace();
        }
    }



    @Override
    protected void takeDown() {
        System.out.println("Finaliza la ejecución del agente: " + this.getName());

        if (gui != null) {
            gui.dispose();
        }
    }

    /**
     * Clase interna que gestiona la interfaz gráfica del agente principal.
     */
    private class AgentePrincipalGUI extends JFrame {
        private JTextField txtNumero1, txtNumero2;
        private JButton btnSumar;
        private JLabel lblResultado;

        public AgentePrincipalGUI() {
            setTitle("Agente Principal - Calculadora");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(4, 2));

            // Componentes de la interfaz
            txtNumero1 = new JTextField();
            txtNumero2 = new JTextField();
            btnSumar = new JButton("Sumar");
            lblResultado = new JLabel("Esperando resultado...");

            // Agregar componentes a la ventana
            add(new JLabel("Número 1:"));
            add(txtNumero1);
            add(new JLabel("Número 2:"));
            add(txtNumero2);
            add(btnSumar);
            add(lblResultado);

            // Acción del botón
            btnSumar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Ejecutar en el hilo del agente
                    addBehaviour(new OneShotBehaviour() {
                        @Override
                        public void action() {
                            lanzarAgenteSecundario();
                        }
                    });
                }
            });

        }

        /**
         * Método para lanzar el AgenteSecundario con los operandos ingresados.
         */
        private void lanzarAgenteSecundario() {
            try {
                double num1 = Double.parseDouble(txtNumero1.getText());
                double num2 = Double.parseDouble(txtNumero2.getText());

                // Obtener el contenedor de JADE
                ContainerController cc = getContainerController(); // Ahora se ejecuta en el hilo del agente

                Object[] args = {num1, num2, getAID().getName()};
                AgentController agenteSecundario = cc.createNewAgent("AgenteSecundario", "es.ujaen.ssmmaa.agentes.AgenteSecundario", args);
                agenteSecundario.start();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Introduce números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (StaleProxyException ex) {
                JOptionPane.showMessageDialog(this, "Error al crear el agente secundario.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }

    }
}
