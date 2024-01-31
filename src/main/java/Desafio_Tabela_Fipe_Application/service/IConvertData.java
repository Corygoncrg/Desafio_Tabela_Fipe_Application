package Desafio_Tabela_Fipe_Application.service;

import java.util.List;

public interface IConvertData {
    <T> T getData(String json, Class<T> tClass);

    <T> List<T> getList (String json, Class<T> tClass);
}
