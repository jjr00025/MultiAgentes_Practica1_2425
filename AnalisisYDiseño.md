# **Análisis y Diseño**

## **1. Descripción general**

## **1.1 Agente Principal**
El **Agente Principal** tiene dos responsabilidades principales:

#### **1.1.1 Mostrar una interfaz gráfica**
Donde el usuario podrá:
    
    - Seleccionar el tipo de operación aritmética.
    - Seleccionar el comportamiento (behaviour) de la operación.
    - Ingresar los operandos necesarios.
    - Confirmar y lanzar la operación.

#### **1.1.2 Registrar informes** de las operaciones que se vayan ejecutando, mostrando:
        
    - Identificador del agente que ejecutó la operación.
    - Operandos utilizados y comportamiento elegido.
    - Resultado de la operación.
    - Tiempo transcurrido en la realización de la operación.

Además, debe crear dinámicamente el **Agente Secundario** en JADE, al que le pasará como argumentos de inicialización los parámetros seleccionados en la interfaz.

## **1.2 Agente Secundario**
El **Agente Secundario** se crea dinámicamente tras la pulsación del botón en la interfaz y debe realizar las siguientes tareas:

#### **1.2.1 Recepción de parámetros**
Al iniciarse el agente recibe los siguientes datos:
        
    - Operandos.
    - Operación.
    - Comportamiento.
    - Identificador del agente principal.

#### **1.2.2 Ejución de la operación**

    - El agente aplica la operatoria correspondiente a los operandos.
    - Puede variar el tiempo de ejecución según el comportamiento seleccionado.

#### **1.2.3 Medición de tiempos**

    - El agente mide el tiempo que tarda en ejecutar la operación.
    - Utiliza `System.currentTimeMillis()` para registrar el inicio y fin del proceso.

#### **1.2.4 Comunicación del resultado**

    - Una vez terminada la operación, el agente envía un mensaje al agente principal.
    - El mensaje incluye el resultado de la operación y el tiempo de ejecución.

## **2. Requisitos funcionales**
### **2.1. Creación de un formulario en Swing que muestre:**
        
    - Un JComboBox para seleccionar la operación (suma, resta, multiplicación, division, etc).
    - Un JComboBox para seleccionar el comportamiento asociado.
    - Campos para ingresar los operandos necesarios.
    - Un botón para confirmar y lanzar la operación.
### **2.2. Lógica de creación de agente:**

    - Al pulsar el botón, se recogerán los parámetros de la operación y el comportamiento.
    - Se instanciará en la plataforma JADE un nuevo agente (Agente Secundario), pasándole los argumentos necesarios (operación, comportamiento, operandos).
### **2.3. Registro de informes en la interfaz:**

    - El Agente Principal debe recibir los resultados del Agente Secundario.
    - Mostrar en una zona de texto o panel toda la información:
        - Identificador del agente que ejecutó la operación.
        - Operandos utilizados y comportamiento elegido.
        - Resultado de la operación.
        - Tiempo transcurrido en la realización de la operación.
## **3. Diagrama de clases y relaciones**

### **3.1. Diagrama de clases**
El diseño de clases propuesto para la práctica es el siguiente:


````</code>
classDiagram
    class AgentePrincipal {
        - mainGUI : VistaPrincipal
        - startTime : long
        + setup() : void
        + createSecondAgent(operation, behavior, operands) : void
        + recibirInformes() : void
    }

    class VistaPrincipal {
        - comboOperacion : JComboBox
        - comboComportamiento : JComboBox
        - operandFields : List<JTextField>
        - botonConfirmar : JButton
        - textAreaInformes : JTextArea
        + initComponents() : void
        + getSelectedOperation() : String
        + getSelectedBehavior() : String
        + getOperands() : List<String or Double>
        + appendReport(report : String) : void
    }

    class Controlador {
        + actionPerformed(event : ActionEvent) : void
    }

    AgentePrincipal --> VistaPrincipal : "Tiene una instancia de"
    AgentePrincipal --> Controlador : "Instancia para manejar eventos"
    VistaPrincipal --> Controlador : "Eventos de la UI"
    
    class AgenteSecundario {
        - operation : String
        - behavior : String
        - operands : List<String or Double>
        - mainAgent : AID
        - startTime : long
        + setup(operation, behavior, operands, mainAgent) : void
        + executeOperation() : void
        + sendReport(result : String) : void
    }
    
    AgentePrincipal --> AgenteSecundario : "Crea una instancia de"
````

### **3.2. Descripción de las clases**

- **AgentePrincipal**: Clase que representa el agente principal de la práctica. Tiene una referencia a la vista principal y un método para configurar la interfaz y crear el agente secundario.
- **VistaPrincipal**: Clase que representa la interfaz gráfica del agente principal. Contiene los componentes Swing necesarios para la interacción con el usuario.
- **Controlador**: Clase que maneja los eventos de la interfaz gráfica y se encarga de la lógica de la aplicación.
- **AgenteSecundario**: Clase que representa el agente secundario de la práctica. Recibe los parámetros de configuración, ejecuta la operación y envía el resultado al agente principal.


### **3.2.1 Atributos y métodos de las clases**

- **AgentePrincipal**:
    - **mainGUI**: Referencia a la vista principal.
    - **startTime**: Marca de tiempo de inicio de la operación.
    - **setup()**: Configura la interfaz y los eventos.
    - **createSecondAgent(operation, behavior, operands)**: Crea un agente secundario con los parámetros recibidos.
    - **recibirInformes()**: Recibe los informes del agente secundario y los muestra en la interfaz.

- **VistaPrincipal**:
  - **comboOperacion**: JComboBox para seleccionar la operación.
  - **comboComportamiento**: JComboBox para seleccionar el comportamiento.
  - **operandFields**: Lista de campos de texto para los operandos.
  - **botonConfirmar**: Botón para confirmar la operación.
  - **textAreaInformes**: JTextArea para mostrar los informes.
  - **initComponents()**: Inicializa los componentes de la interfaz.
  - **getSelectedOperation()**: Obtiene la operación seleccionada.
  - **getSelectedBehavior()**: Obtiene el comportamiento seleccionado.
  - **getOperands()**: Obtiene los operandos ingresados.
  - **appendReport(report)**: Agrega un informe al área de informes.

- **Controlador**:
  - **actionPerformed(event)**: Maneja los eventos de la interfaz.

- **AgenteSecundario**:
  - **operation**: Operación a realizar.
  - **behavior**: Comportamiento asociado.
  - **operands**: Operandos de la operación.
  - **mainAgent**: Identificador del agente principal.
  - **startTime**: Marca de tiempo de inicio de la operación.
  - **setup(operation, behavior, operands, mainAgent)**: Configura el agente secundario.
  - **executeOperation()**: Ejecuta la operación.
  - **sendReport(result)**: Envía el resultado al agente principal.

### **3.3. Métodos más relevantes**

- **AgentePrincipal.setup()**: Configura la interfaz y los eventos de la aplicación.
````</code>
    protected void setup() {
    System.out.println("Agente Principal iniciado: " + getLocalName());
    
    // Configurar la vista
    vista = new VistaPrincipal(this);
    vista.setVisible(true);

    // Registrar comportamiento para recibir informes
    addBehaviour(new RecibirInformesBehaviour(this));
}

````

- **AgentePrincipal.createSecondAgent()**: Crea un agente secundario con los parámetros recibidos.
````</code>
public void createSecondAgent(String operation, String behavior, Object[] operands) {
    try {
        ContainerController container = getContainerController();
        Object[] args = new Object[]{ operation, behavior, operands };
        AgentController ac = container.createNewAgent(
            "AgenteSecundario_" + System.currentTimeMillis(),
            "path.to.agent.AgenteSecundario",
            args
        );
        ac.start();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
````   

- **AgentePrincipal.recibirInformes()**: Recibe los informes del agente secundario y los muestra en la interfaz.
````</code>
public void recibirInformes(String report) {
    vista.appendReport(report);
}
````

- **AgenteSecundario.setup()**: Configura el agente secundario con los parámetros recibidos.
````</code>
protected void setup() {
    System.out.println("Agente Secundario iniciado: " + getLocalName());

    // Recuperar argumentos
    Object[] args = getArguments();
    if (args != null && args.length >= 3) {
        this.operation = (String) args[0];
        this.behaviorType = (String) args[1];
        this.operands = (Object[]) args[2];

        if (args.length > 3 && args[3] instanceof String) {
            this.principalName = (String) args[3];
        }
    }

    // Registrar comportamiento para realizar la operación
    addBehaviour(new RealizarOperacionBehaviour(this));
}
````

- **AgenteSecundario.executeOperation()**: Ejecuta la operación y envía el resultado al agente principal.
````</code>
public double executeOperation() {
    // Se asume que operands contiene, al menos, dos operandos.
    double op1 = Double.valueOf(operands[0].toString());
    double op2 = Double.valueOf(operands[1].toString());
    double result = 0.0;

    switch (operation.toLowerCase()) {
        case "suma":
            result = op1 + op2;
            break;
        case "resta":
            result = op1 - op2;
            break;
        case "multiplicacion":
            result = op1 * op2;
            break;
        case "division":
            if (op2 != 0) {
                result = op1 / op2;
            } else {
                System.out.println("Error: División entre cero");
            }
            break;
        default:
            System.out.println("Operación no reconocida: " + operation);
    }

    return result;
}
````

- **AgenteSecundario.sendResultToPrincipal()**: Envía el resultado al agente principal.
````</code>
public void sendResultToPrincipal(double result) {
    // Construir mensaje ACL
    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

    // Localizar al principal por nombre local
    if (principalName != null && !principalName.isEmpty()) {
        AID principalAID = new AID(principalName, AID.ISLOCALNAME);
        msg.addReceiver(principalAID);
    } else {
        // Si no hay principalName, podría usarse DFService o mostrar un error
        System.out.println("No se proporcionó nombre del agente principal.");
        return;
    }

    // Preparar contenido (se puede usar un formato plano o JSON, etc.)
    msg.setContent("Operación: " + operation + 
                   ", Resultado: " + result + 
                   ", Comportamiento: " + behaviorType);

    // Enviar mensaje
    send(msg);

    System.out.println("AgenteSecundario: Mensaje enviado a " + principalName +
                       " con resultado = " + result);
}
````

## **4. Behaviour**

### **4.1. AgentePrincipal.RecibirInformesBehaviour**
Este comportamiento se encarga de recibir los informes enviados por los agentes secundarios y mostrarlos en la interfaz gráfica del agente principal.
````</code>
public class RecibirInformesBehaviour extends CyclicBehaviour {
    public RecibirInformesBehaviour(Agent a) {
        super(a);
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();
        if (msg != null) {
            if (msg.getPerformative() == ACLMessage.INFORM) {
                String reporte = msg.getContent();
                // Llamar al método del agente para manejar el informe
                ((AgentePrincipal) myAgent).recibirInformes(reporte);
            }
        } else {
            block();
        }
    }
}
````

### **4.2. AgenteSecundario.RealizarOperacionBehaviour**
Este comportamiento se encarga de realizar la operación aritmética correspondiente y enviar el resultado al agente principal.
````</code>
public class RealizarOperacionBehaviour extends OneShotBehaviour {

    public RealizarOperacionBehaviour(Agent a) {
        super(a);
    }

    @Override
    public void action() {
        AgenteSecundario as = (AgenteSecundario) myAgent;

        // 1. Ejecutar la operación
        double result = as.executeOperation();

        // 2. Enviar el resultado al Agente Principal
        as.sendResultToPrincipal(result);

        // 3. Finalizar el Agente Secundario (opcional, si se desea descartar el agente)
        myAgent.doDelete();
    }
}
````

## **5. Conclusiones**

El análisis y diseño de la práctica es fundamental para comprender los requisitos y la estructura de la solución a implementar. En este caso, se ha definido la arquitectura de agentes y la interacción entre ellos, así como la interfaz gráfica y los comportamientos necesarios para realizar las operaciones aritméticas. Este análisis facilita la implementación y permite tener una visión clara de los componentes y su funcionamiento en conjunto.