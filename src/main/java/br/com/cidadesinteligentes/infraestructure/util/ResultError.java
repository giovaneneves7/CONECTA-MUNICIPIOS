package br.com.cidadesinteligentes.infraestructure.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;

/**
 * @author Jorge Roberto
 */
public class ResultError {


    /**
     * Retrieves the validation errors from a request and stores them in a map.
     *
     * @param result - The object containing the errors.
     * @return a map with the request's validation errors.
     */
    public static HashMap<String, String> getResultErrors(BindingResult result){

        HashMap<String, String> erros = new HashMap<>();

        for(FieldError erro : result.getFieldErrors())
            erros.put(erro.getField(), erro.getDefaultMessage());

        return erros;

    }
}