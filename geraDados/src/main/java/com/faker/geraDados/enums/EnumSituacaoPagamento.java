package com.faker.geraDados.enums;

import lombok.Getter;

@Getter
public enum EnumSituacaoPagamento {
    ABERTO(),
    PAGO(),
    ATRASADO();

    EnumSituacaoPagamento() {
    }
}
