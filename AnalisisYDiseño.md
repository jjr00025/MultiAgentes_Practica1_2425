# **Análisis y Diseño**

## **1. Descripción general**
El **Agente Principal** tiene dos responsabilidades principales:

### **1.1 Mostrar una interfaz gráfica**
Donde el usuario podrá:
    
    - Seleccionar el tipo de operación aritmética.
    - Seleccionar el comportamiento (behaviour) de la operación.
    - Ingresar los operandos necesarios.
    - Confirmar y lanzar la operación.

### **1.2 Registrar informes** de las operaciones que se vayan ejecutando, mostrando:
        
    - Identificador del agente que ejecutó la operación.
    - Operandos utilizados y comportamiento elegido.
    - Resultado de la operación.
    - Tiempo transcurrido en la realización de la operación.

Además, debe crear dinámicamente el **Agente Secundario** en JADE, al que le pasará como argumentos de inicialización los parámetros seleccionados en la interfaz.

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
    class AgentePrincipal{
        +void mostrarInterfaz()
        +void registrarInforme()
    }
    class AgenteSecundario{
        +void recibirParametros()
        +void crearAgente()
        +void enviarResultado()
    }
    AgentePrincipal "1" --> "1..*" AgenteSecundario


