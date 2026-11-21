# language: es
Característica: Matrícula del alumno con dígito D=1
  Como estudiante de Pruebas de Software
  Quiero automatizar el flujo con los datos de mi fila
  Para verificar la matrícula, el pago y la regla que debe fallar

  Antecedentes:
    Dado que el alumno "i202610001" inicia sesión con la clave "Cibertec2026" y obtiene un token Bearer

  Escenario: Cotizar el curso de mi fila
    Cuando cotizo el curso "PI5620" para el alumno "i202610001"
    Entonces la respuesta de cotización tiene el estado 200
    Y la cotización tiene 6 créditos, subtotal "720.0", descuento 20 y total "576.0"
    Y la respuesta JSON cumple el esquema "cotizacion.json"

  Escenario: Matricularse y pagar el curso de mi fila
    Cuando matrico al alumno "i202610001" en el curso "PI5620"
    Entonces la respuesta de matrícula tiene el estado 201
    Y la matrícula tiene estado "REGISTRADA" y monto "576.0"
    Y la respuesta JSON cumple el esquema "matricula.json"
    Cuando pago la matrícula creada con el método "TARJETA"
    Entonces la respuesta de pago tiene el estado 200
    Y la matrícula tiene estado "PAGADA" y fechaPago con valor
    Y la respuesta JSON cumple el esquema "matricula.json"
    Cuando consulto los pagos del alumno "i202610001"
    Entonces existe un pago para la matrícula creada con método "TARJETA" y número de operación con formato "OP-000000"

  Escenario: Regla de negocio que debe fallar
    Cuando cotizo los cursos que deben fallar:
      | PS5511 |
      | QA5517 |
      | DM5512 |
      | TS5514 |
      | SA5516 |
      | BD5410 |
    Entonces la respuesta de error tiene el estado 422
    Y el error tiene el codigo "LIMITE_CREDITOS"
    Y la respuesta JSON cumple el esquema "error.json"
