package com.upchiapas.songsapp.auth.data.mapper

import com.upchiapas.songsapp.auth.data.dto.UsuarioDto
import com.upchiapas.songsapp.auth.domain.model.Usuario

fun UsuarioDto.toDomain(): Usuario = Usuario(
    id = id,
    nombre = nombre,
    email = email
)
