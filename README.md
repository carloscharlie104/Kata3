# Kata3
Voy a extender la funcionalidad para computar frecuencias en memoria. Para evitar el acoplamiento a atributos específicos, 
implementaré un mecanismo de selección dinámica mediante inyección de dependencias; es decir, inyectaré el criterio de agrupación 
vía lambda o referencia a método. La estructura de datos resultante (las frecuencias) quedará encapsulada en una nueva clase Histogram.
