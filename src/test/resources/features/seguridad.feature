# language: es
Característica: Seguridad de la sesión

  Antecedentes:
    Dado que Marilyn consulta la API de matricula

  Escenario: Rotación del refresh token
    Cuando IniciarSesion con el usuario "i202610001" y la clave "Cibertec2026"
    Entonces el codigo de respuesta es 200
    Y los tokens quedaron en la memoria del actor
    Cuando RenovarToken con el refresh token guardado
    Entonces el codigo de respuesta es 200
    Y los tokens nuevos son diferentes a los originales
    Cuando reutilizo el refresh token original
    Entonces el codigo de respuesta es 401
    Y el codigo del error de seguridad es "REFRESH_REUTILIZADO"
    Y la respuesta cumple el esquema "error.json"
    Cuando consulto el perfil con el accessToken nuevo
    Entonces el codigo de respuesta es 401
    Y el codigo del error de seguridad es "TOKEN_INVALIDO"
    Y la respuesta cumple el esquema "error.json"

  Escenario: Cierre de sesión
    Cuando IniciarSesion con el usuario "i202610001" y la clave "Cibertec2026"
    Entonces el codigo de respuesta es 200
    Cuando CerrarSesion con el accessToken guardado
    Entonces el codigo de respuesta es 204
    Cuando consulto el perfil con el accessToken guardado
    Entonces el codigo de respuesta es 401
    Y el codigo del error de seguridad es "TOKEN_INVALIDO"
    Y la respuesta cumple el esquema "error.json"

  Escenario: Bloqueo de cuenta por intentos fallidos
    Cuando intento iniciar sesión con el usuario "i202610004" según la tabla:
      | clave          | estado | codigo                 |
      | Incorrecta2026 | 401    | CREDENCIALES_INVALIDAS |
      | Incorrecta2026 | 401    | CREDENCIALES_INVALIDAS |
      | Incorrecta2026 | 401    | CREDENCIALES_INVALIDAS |
      | Incorrecta2026 | 401    | CREDENCIALES_INVALIDAS |
      | Incorrecta2026 | 423    | CUENTA_BLOQUEADA       |
    Entonces intento iniciar sesión con la clave correcta "Cibertec2026"
    Y el codigo de respuesta es 423
    Y el codigo del error de seguridad es "CUENTA_BLOQUEADA"
    Y la respuesta cumple el esquema "error.json"

  Escenario: Alumno sin permiso de administrador
    Cuando IniciarSesion con el usuario "i202610001" y la clave "Cibertec2026"
    Entonces el codigo de respuesta es 200
    Cuando listo todos los alumnos con el accessToken guardado
    Entonces el codigo de respuesta es 403
    Y el codigo del error de seguridad es "ACCESO_DENEGADO"
    Y la respuesta cumple el esquema "error.json"
