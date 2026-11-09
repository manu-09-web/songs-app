# Songs App

App para Android que sirve para llevar el repertorio de una banda: cada integrante se registra, inicia sesión y administra sus canciones (título, artista, tonalidad, tempo, duración, fecha del último ensayo, acordes, letra y qué tanto la dominan).

Proyecto de la Unidad 3 de Desarrollo de Aplicaciones Móviles (Android nativo).

La API que usa la app está en [songs-app-api](https://github.com/manu-09-web/songs-app-api) y está desplegada en `https://songsapp.duckdns.org/api/v1/`.

## Funciones

- Crear cuenta, iniciar sesión, recordar la sesión y cerrarla
- Ver el repertorio con un resumen y filtrar por estado (dominada, a medias o sin dominar)
- Ver el detalle de una canción con sus acordes y su letra
- Agregar, editar y eliminar canciones (con confirmación antes de eliminar)
- Mensajes de carga, lista vacía, error de conexión y sesión vencida

## Tecnologías

- Kotlin + Jetpack Compose (Material 3)
- MVVM con `ViewModel`, `StateFlow` y corrutinas
- Retrofit 3 + kotlinx.serialization + OkHttp (el token va en un interceptor)
- Navigation Compose
- SharedPreferences para guardar la sesión
- Inyección de dependencias manual con `AppContainer`

## Estructura

```
app/src/main/java/com/upchiapas/songsapp/
  auth/        registro e inicio de sesión (data, domain, presentation)
  canciones/   repertorio, detalle y formulario (data, domain, presentation)
  core/        AppContainer, red, sesión y navegación
  ui/          tema y componentes compartidos
```

## Cómo correrlo

1. Abrir la carpeta en Android Studio y esperar a que sincronice Gradle.
2. Ejecutar la configuración `app` en un emulador o celular con Android 8.0 o superior.

La app se conecta a la API en AWS, que debe estar encendida. La dirección se puede cambiar con `api.url` en `local.properties` (solo HTTPS).
