package com.example.guiex1.domain;

import java.util.Objects;

public class PrietenieValidator implements Validator<Prietenie> {
    public PrietenieValidator() {
    }

    public void validate(Prietenie entity) throws ValidationException {
        if (Objects.equals(entity.getId1(), entity.getId2())) {
            throw new ValidationException("nu se poate realiza prietenie cu tine inusti");
        }
    }
}