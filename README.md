[![logo](https://www.gnu.org/graphics/gplv3-127x51.png)](https://choosealicense.com/licenses/gpl-3.0/)

# Práctica: Primeros Agentes con JADE y Swing

## Objetivos

1.  **Primer contacto con JADE:** Aprender a configurar e integrar la biblioteca JADE en un proyecto Java-Maven para desarrollar agentes.
2.  **Desarrollo de interfaces gráficas:** Implementar una interfaz en Java Swing que permita la entrada de parámetros para configurar agentes.
3.  **Creación y comunicación entre agentes:** Diseñar dos agentes, uno que actúe como interfaz y coordinador, y otro que realice operaciones, comunicándose mediante los mecanismos de mensajería de JADE.
4.  **Registro y monitoreo:** Implementar un sistema que registre en la interfaz los informes de ejecución, incluyendo el resultado de la operación y el tiempo que ha llevado realizarla.

----------

## Descripción de la Práctica

### 1. Diseño e implementación del agente principal (con interfaz Swing)

El agente principal tendrá dos funciones principales:

-   **Interfaz gráfica:**  
    Debe mostrar un formulario que permita al usuario configurar los parámetros de una operación. La interfaz deberá incluir:
    
    -   **Selección de operación:**  
        Un componente (por ejemplo, un `JComboBox`) que ofrezca una lista predefinida de operaciones matemáticas (por ejemplo: _suma, resta, multiplicación, división_). Estas operaciones serán las que el alumno quiera y se valora que puedan configurarse cuando se inicia el agente principal como parte de su configuración de inicio.
    -   **Selección del comportamiento:**  
        Otro `JComboBox` o similar que permita seleccionar el comportamiento de resolución de la operación (por ejemplo: _comportamiento general definido por el usuario, operación con un número de ciclos_, etc.).  
        _Nota:_ Los comportamientos estarán programados y, al igual que las operaciones, se valora que puedan configurarse al inicio del agente principal.
    -   **Campos para operandos:**  
        Dependiendo de las operaciones que el alumno defina para la práctica serán los datos necesarios para que el comportamiento pueda completarse.
    -   **Botón de confirmación:**  
        Al pulsar el botón, se debe:
        -   Recoger los parámetros de la operación (tipo de operación, comportamiento y operandos).
        -   Crear de forma dinámica un segundo agente en la plataforma JADE, pasando como argumentos de inicialización los parámetros recogidos.
        
-   **Registro de informes:**  
    La interfaz deberá disponer de un área (por ejemplo, un `JTextArea` o un panel dedicado) donde se muestren los informes de ejecución recibidos de los agentes. Cada entrada en el informe deberá incluir:
    
    -   El identificador del agente que ejecutó la operación.
    -   Los operandos que formaron parte de la operación y el comportamiento elegido.
    -   El resultado de la operación.
    -   El tiempo transcurrido en la realización de la operación. Es decir, el tiempo transcurrido desde que se creó el agente y recibe la información para crear el informe.

### 2. Diseño e implementación del agente secundario

Este agente se crea dinámicamente tras la pulsación del botón en la interfaz y debe realizar las siguientes tareas:

-   **Recepción de parámetros:**  
    Al iniciarse, el agente recibe los parámetros de configuración (operandos, operación y comportamiento) que se le han pasado.
    
-   **Creación del agente:**  
    Se utiliza el método que permite crear un agente dentro de la clase `MicroRuntime`:
    -   Los valores de configuración del agente se pasan como parámetros en su creación.
    -   Mide el tiempo que tarda en ejecutarla (por ejemplo, utilizando `System.currentTimeMillis()` para registrar el inicio y fin del proceso). Estos cálculos los realiza el agente principal para su informe.
    
-   **Comunicación del resultado:**  
    Una vez terminada la operación, el agente envía un mensaje (usando los mecanismos de mensajería de JADE) al agente principal. Este mensaje debe incluir:
    -   El resultado de la operación.
 
 Entre los parámetros debe estar nombre del agente principal para poder localizarlo en la plataforma. El método de localización será el que el alumno elija y deberá documentarlo como parte del diseño de la práctica.

### 3. Comunicación entre agentes

Utilizar los métodos de envío y recepción de mensajes de JADE para establecer la comunicación entre el agente secundario y el agente principal. Se recomienda:

### 4. Visualización y actualización de informes

El agente principal, al recibir el mensaje del agente secundario, actualizará la sección de informes de la interfaz. Cada nuevo informe deberá:

-   Añadir la información recibida.
-   Permitir visualizar el histórico de operaciones realizadas durante la sesión.
-   Se debe crear un fichero a la finalización del agente principal con todos los informes generados durante su ejecución.

----------

## Criterios de Evaluación

La evaluación se realizará teniendo en cuenta la calidad y el correcto funcionamiento de la solución, diferenciándose en tres niveles:

### **Nivel Mínimo**

-   **Configuración y compilación:**
    -   La aplicación compila y se ejecuta sin errores graves. La forma de ejecución se detalla en las instrucciones de entrega de la práctica.
-   **Funcionalidad básica:**
    -   Se implementa el agente principal con una interfaz Swing básica que permite recoger los parámetros (operandos, operación y comportamiento).
    -   Al pulsar el botón de confirmación, se crea un agente secundario que realiza la operación matemática.
    -   El agente secundario envía el resultado y el tiempo de ejecución al agente principal.
    -   La interfaz muestra un informe básico con el resultado de la operación y el tiempo empleado.
- **Documentación mínima de análisis y diseño de la práctica**

### **Nivel Notable**

-   **Interfaz y experiencia de usuario:**
    -   La interfaz Swing es intuitiva y amigable, con una correcta disposición de los elementos.
    -   Se implementa validación básica de la entrada (por ejemplo, comprobación de que los operandos tengan el formato correcto para la operación seleccionada y manejo de errores elementales).
-   **Registro detallado:**
    -   El informe de ejecución en la interfaz incluye información adicional (por ejemplo, identificación del agente, marcas de tiempo de inicio y fin de la operación).
-   **Documentación:**
    -   El código cuenta con comentarios explicativos que facilitan la comprensión del funcionamiento.
    -   La documentación de análisis y diseño utiliza los elementos de formato adecuadamente para una lectura amigable y las explicaciones son detalladas y bien justificadas.

### **Nivel Sobresaliente**

-   **Diseño modular y extensible:**
    -   La solución demuestra una clara orientación a objetos y modularidad, permitiendo la fácil extensión o modificación de funcionalidades.
-   **Interfaz avanzada:**
    -   La interfaz Swing ofrece mejoras significativas, tales como validaciones en tiempo real, manejo avanzado de errores (por ejemplo, notificaciones emergentes) y una experiencia de usuario pulida.
-   **Informe profesional:**
    -   El sistema de informes no solo muestra textos, sino que también utiliza elementos gráficos o tablas para presentar la información de manera clara y profesional.
-   **Calidad del código:**
    -   El código está altamente documentado, incluye pruebas de integración que demuestran su correcto funcionamiento, y se siguen las mejores prácticas de programación.
