# Songs App

App para Android que sirve para llevar el repertorio de una banda: cada integrante se registra, inicia sesión y administra sus canciones (título, artista, tonalidad, tempo, duración, fecha del último ensayo, acordes, letra y si ya la dominan).

Proyecto de la Unidad 3 de Desarrollo de Aplicaciones Móviles (Android nativo).

## Tecnologías

- Kotlin + Jetpack Compose (Material 3)
- MVVM con `ViewModel` y `StateFlow`
- Retrofit 3 + kotlinx.serialization + OkHttp
- Navigation Compose
- SharedPreferences para guardar la sesión
- Backend propio: FastAPI + MySQL (en `backend/`, pendiente)

## Estructura

```
app/        Proyecto de Android
```

## Cómo correrlo

1. Abrir la carpeta en Android Studio.
2. Esperar a que sincronice Gradle.
3. Ejecutar la configuración `app` en un emulador (API 26 o mayor).
