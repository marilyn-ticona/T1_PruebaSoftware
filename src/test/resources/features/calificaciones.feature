# language: es
Característica: Calificación de matrículas

  Antecedentes:
    Dado que Marilyn consulta la API de matricula

  Esquema del escenario: El docente D0001 registra la nota y el historial se actualiza
    Dado que inicio sesión como la alumna "i202610005" con la clave "Cibertec2026" y guardo su token
    Y obtengo el id de la matrícula del curso "PI5620" para "i202610005" desde GET /api/matriculas con el token de la alumna
    Cuando el docente "D0001" inicia sesión con la clave "Cibertec2026" y guarda su token
    Y CalificarMatricula registra la nota "<nota>" en la matrícula obtenida
    Entonces el codigo de respuesta es 200
    Y la condición de la matrícula es "<condicion>"
    Cuando ConsultarHistorial consulta el historial de "i202610005"
    Entonces el codigo de respuesta es 200
    Y la respuesta cumple el esquema "historial.json"
    Y el historial tiene <creditos> créditos aprobados y promedio ponderado "<promedio>"

    Ejemplos:
      | nota  | condicion    | creditos | promedio |
      | 11    | DESAPROBADO  | 7        | 13.38    |
      | 12,5  | DESAPROBADO  | 7        | 14.08    |

  Escenario: Nota 12.5 escrita con punto decimal
    Dado que inicio sesión como la alumna "i202610005" con la clave "Cibertec2026" y guardo su token
    Y obtengo el id de la matrícula del curso "PI5620" para "i202610005" desde GET /api/matriculas con el token de la alumna
    Cuando el docente "D0001" inicia sesión con la clave "Cibertec2026" y guarda su token
    Y CalificarMatricula registra la nota "12.5" en la matrícula obtenida
    Entonces el codigo de respuesta es 200
    Y la condición de la matrícula es "DESAPROBADO"
    Cuando ConsultarHistorial consulta el historial de "i202610005"
    Entonces el codigo de respuesta es 200
    Y la respuesta cumple el esquema "historial.json"
    Y el historial tiene 7 créditos aprobados y promedio ponderado "14.08"

  Escenario: Un docente que no dicta el curso intenta registrar la nota
    Dado que inicio sesión como la alumna "i202610005" con la clave "Cibertec2026" y guardo su token
    Y obtengo el id de la matrícula del curso "PI5620" para "i202610005" desde GET /api/matriculas con el token de la alumna
    Cuando el docente "D0002" inicia sesión con la clave "Cibertec2026" y guarda su token
    Y CalificarMatricula registra la nota "15" en la matrícula obtenida
    Entonces el codigo de respuesta es 403
    Y el codigo del error de seguridad es "ACCESO_DENEGADO"
    Y la respuesta cumple el esquema "error.json"

  Escenario: No se puede calificar una matrícula no pagada
    Dado que inicio sesión como la alumna "i202610005" con la clave "Cibertec2026" y guardo su token
    Y obtengo el id de la matrícula del curso "PS5511" para "i202610005" desde GET /api/matriculas con el token de la alumna
    Cuando el docente "D0001" inicia sesión con la clave "Cibertec2026" y guarda su token
    Y CalificarMatricula registra la nota "15" en la matrícula obtenida
    Entonces el codigo de respuesta es 409
    Y el codigo del error de seguridad es "MATRICULA_NO_PAGADA"
    Y la respuesta cumple el esquema "error.json"
