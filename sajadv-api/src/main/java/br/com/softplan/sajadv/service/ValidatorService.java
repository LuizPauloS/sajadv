package br.com.softplan.sajadv.service;

import br.com.softplan.sajadv.exception.ApiValidationException;
import javax.validation.*;
import java.util.*;
import java.util.stream.Collectors;

public class ValidatorService {

    public static <T> boolean validarAtributosEntidade(T entidade) throws ApiValidationException {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<T>> violations = validator.validate(entidade);

        if (!violations.isEmpty()) {
            List<String> errors = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            throw new ApiValidationException("Campos inválidos", errors);
        }
        return true;
    }
}
