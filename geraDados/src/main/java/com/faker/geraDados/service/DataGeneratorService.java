package com.faker.geraDados.service;

import com.faker.geraDados.entity.*;
import com.faker.geraDados.enums.*;
import com.faker.geraDados.repository.*;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Service
public class DataGeneratorService {

    @Autowired
    private AssociacaoRepository associacaoRepository;
    @Autowired
    private InstituicaoRepository instituicaoRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ParametroRepository parametroRepository;
    @Autowired
    private PagamentoRepository pagamentoRepository;

    private final Faker faker = new Faker();
    private final Random random = new Random();

    public void generateData() {
        generateAssociacaos(50);
        generateInstituicaos(250);
        generateCursos(5000);
        generateUsuarios(2500);
        generateParametros(50);
        generatePagamentos();
    }

    private void generateAssociacaos(int count) {
        IntStream.range(0, count).forEach(i -> {
            Associacao associacao = new Associacao();
            associacao.setCnpj(faker.number().randomNumber());
            associacao.setNome(faker.company().name());
            associacao.setEndereco(faker.address().fullAddress());
            associacao.setSituacao(EnumAssociacaoSituacao.values()[random.nextInt(EnumAssociacaoSituacao.values().length)]);
            associacao.setCreatedAt(randomDate());
            associacao.setUpdatedAt(randomDate());
            associacaoRepository.save(associacao);
        });

        System.out.println("passou associação");
    }

    private void generateInstituicaos(int count) {
        IntStream.range(0, count).forEach(i -> {
            Instituicao instituicao = new Instituicao();
            instituicao.setNome(faker.company().name());
            instituicao.setEndereco(faker.address().fullAddress());
            instituicao.setSituacao(EnumInstituicaoSituacao.ATIVO);
            long assoId = 1;
            if (count % 5 == 0) {
                assoId += 1;
            }
            instituicao.setAssociacao(associacaoRepository.findById(assoId).orElse(null));
            instituicao.setCreatedAt(randomDate());
            instituicao.setUpdatedAt(randomDate());
            instituicaoRepository.save(instituicao);
        });

        System.out.println("passou instituição");
    }

    private void generateCursos(int count) {
        IntStream.range(0, count).forEach(i -> {
            Curso curso = new Curso();
            curso.setNome(faker.educator().course());
            curso.setSituacao(EnumCursosSituacao.ATIVO);
            long instId = 1;
            if (count % 20 == 0) {
                instId += 1;
            }
            curso.setInstituicao(instituicaoRepository.findById(instId).orElse(null)); // Adjust as necessary
            curso.setCreatedAt(randomDate());
            curso.setUpdatedAt(randomDate());
            cursoRepository.save(curso);
        });

        System.out.println("passou cursos");
    }

    private void generateUsuarios(int count) {
        IntStream.range(0, count).forEach(i -> {
            Usuario usuario = new Usuario();
            usuario.setNome(faker.name().fullName());
            String novoEmail = faker.internet().emailAddress();
            while (usuarioRepository.existsByEmail(novoEmail)) {
                novoEmail = faker.internet().emailAddress();
            }
            usuario.setEmail(novoEmail);
            usuario.setTelefone(faker.phoneNumber().phoneNumber());
            usuario.setEndereco(faker.address().fullAddress());
            usuario.setMatricula(faker.idNumber().valid());
            usuario.setCurso(cursoRepository.findById((long) (random.nextInt(4998) + 1)).orElse(null)); // Adjust as necessary
            usuario.setAssociacao(usuario.getCurso().getInstituicao().getAssociacao()); // Adjust as necessary
            usuario.setTipoAcesso(EnumTipoAcesso.ALUNO);
            usuario.setSenha(faker.internet().password());
            usuario.setSituacao(EnumUsuarioSituacao.ATIVO);
            List<EnumUsuariosDiasUsoTransporte> diasUsoTransportes = new ArrayList<>();
            for (int j = 0; j < (random.nextInt(4) + 1); j++) {
                EnumUsuariosDiasUsoTransporte enumUsuariosDiasUsoTransporte = EnumUsuariosDiasUsoTransporte.values()[random.nextInt(EnumUsuariosDiasUsoTransporte.values().length)];
                diasUsoTransportes.add(enumUsuariosDiasUsoTransporte);
            }
            StringBuilder sb = new StringBuilder();
            diasUsoTransportes.forEach(action -> {
                sb.append(action).append(",");
            });
            sb.deleteCharAt(sb.length() - 1);
            usuario.setDiasUsoTransporte(sb.toString());
            usuario.setCreatedAt(randomDate());
            usuario.setUpdatedAt(randomDate());
            usuarioRepository.save(usuario);
        });

        System.out.println("passou usuarios");
    }

    private void generateParametros(int count) {
        IntStream.range(0, count).forEach(i -> {
            Parametro parametro = new Parametro();
            parametro.setAssociacao(associacaoRepository.findById((long) i + 1).get());
            parametro.setValor1(faker.number().numberBetween(100, 1000));
            parametro.setValor2(faker.number().numberBetween(100, 1000));
            parametro.setValor3(faker.number().numberBetween(100, 1000));
            parametro.setValor4(faker.number().numberBetween(100, 1000));
            parametro.setValor5(faker.number().numberBetween(100, 1000));
            parametro.setValor6(faker.number().numberBetween(100, 1000));
            parametro.setValorMulta(faker.number().numberBetween(10, 100));
            parametro.setDiaVencimento(faker.number().numberBetween(1, 28));
            parametro.setDiaAberturaPagamentos(faker.number().numberBetween(1, 28));
            parametro.setDiasToleranciaMulta(faker.number().numberBetween(1, 10));
            parametro.setLiberaAlteracaoDadosPessoais(EnumParametrosLiberaAlteracaoDadosPessoais.values()[random.nextInt(EnumParametrosLiberaAlteracaoDadosPessoais.values().length)]);
            parametro.setCreatedAt(randomDate());
            parametro.setUpdatedAt(randomDate());
            parametroRepository.save(parametro);
        });

        System.out.println("passou parametro");
    }

    private void generatePagamentos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        usuarios.forEach(usuario -> {
            LocalDateTime startDate = LocalDateTime.of(2022, 1, 1, 0, 0);
            LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 23, 59);

            while (startDate.isBefore(endDate)) {
                Pagamento pagamento = new Pagamento();
                pagamento.setUsuario(usuario);
                pagamento.setTipo(EnumPagamentoTipo.values()[random.nextInt(EnumPagamentoTipo.values().length)]);
                pagamento.setValor(faker.number().numberBetween(20, 80));
                pagamento.setMulta(0);
                pagamento.setDataVencimento(startDate);
                pagamento.setDataPagamento(randomDateWithinMonth(startDate));
                pagamento.setSituacaoPagamento(EnumSituacaoPagamento.values()[random.nextInt(EnumSituacaoPagamento.values().length)]);
                if (pagamento.getSituacaoPagamento().equals(EnumSituacaoPagamento.ATRASADO)) {
                    pagamento.setMulta(faker.number().numberBetween(5, 15));
                }
                pagamento.setCreatedAt(randomDate());
                pagamento.setUpdatedAt(randomDate());
                pagamentoRepository.save(pagamento);

                startDate = startDate.plusMonths(1);
            }
        });

        System.out.println("passou pagamentos");
    }
    
    private LocalDateTime randomDate() {
        return LocalDateTime.ofInstant(faker.date().past(1095, java.util.concurrent.TimeUnit.DAYS).toInstant(), ZoneId.systemDefault());
    }
    
    private LocalDateTime randomDateWithinMonth(LocalDateTime date) {
        int day = random.nextInt(date.getMonth().length(date.toLocalDate().isLeapYear())) + 1;
        return LocalDateTime.of(date.getYear(), date.getMonth(), day, random.nextInt(24), random.nextInt(60));
    }
}
