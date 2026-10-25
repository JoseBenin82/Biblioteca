package com.aluracursos.Biblioteca.service;

public interface IConvierteDatos {

    <T>T obtenerDatos(String json,Class<T>clase);
}
