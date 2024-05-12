package br.com.alura.TabelaFlipe.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
