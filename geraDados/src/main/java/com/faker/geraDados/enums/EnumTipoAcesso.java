package com.faker.geraDados.enums;

import lombok.Getter;

@Getter
public enum EnumTipoAcesso {
    ADMIN(),
    GESTAO(),
    ALUNO();

    EnumTipoAcesso() {
    }
}
