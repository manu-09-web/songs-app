# Songs App

App para Android que sirve para llevar el repertorio de una banda: cada integrante se registra, inicia sesión y administra sus canciones (título, artista, tonalidad, tempo, duración, fecha del último ensayo, acordes, letra y qué tanto la dominan).

Proyecto de la Unidad 3 de Desarrollo de Aplicaciones Móviles (Android nativo).

La API que usa la app está en [songs-app-api](https://github.com/manu-09-web/songs-app-api).

## Tecnologías

- Kotlin + Jetpack Compose (Material 3)
- MVVM con `ViewModel` y `StateFlow`
- Retrofit 3 + kotlinx.serialization + OkHttp
- Navigation Compose
- SharedPreferences para guardar la sesión

## Cómo correrlo

1. Arrancar la API (ver [songs-app-api](https://github.com/manu-09-web/songs-app-api)).
2. Abrir la carpeta en Android Studio.
3. Esperar a que sincronice Gradle.
4. Ejecutar la configuración `app` en un emulador (API 26 o mayor). El emulador llega a la API en `http://10.0.2.2:8000/api/v1/`.
