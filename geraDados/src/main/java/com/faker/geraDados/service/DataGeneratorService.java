package com.faker.geraDados.service;

import com.faker.geraDados.entity.*;
import com.faker.geraDados.enums.*;
import com.faker.geraDados.repository.*;
import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
    private TokenAutenticacaoRepository tokenAutenticacaoRepository;
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private ImagemRepository imagemRepository;

    private final Faker faker = new Faker();
    private final Random random = new Random();

    public void generateData() {
        generateAssociacaos(12000);
        generateInstituicaos(10000);
        generateCursos(10000);
        generateUsuarios(10000);
        generateParametros(11000);
        generateTokenAutenticacaos(10000);
        generatePagamentos(10000);
        generateImagems(10000);
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
            instituicao.setSituacao(EnumInstituicaoSituacao.values()[random.nextInt(EnumInstituicaoSituacao.values().length)]);
            instituicao.setAssociacao(associacaoRepository.findById((long) (random.nextInt(10000) + 1)).orElse(null)); // Adjust as necessary
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
            curso.setSituacao(EnumCursosSituacao.values()[random.nextInt(EnumCursosSituacao.values().length)]);
            curso.setInstituicao(instituicaoRepository.findById((long) (random.nextInt(10000) + 1)).orElse(null)); // Adjust as necessary
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
            usuario.setCurso(cursoRepository.findById((long) (random.nextInt(10000) + 1)).orElse(null)); // Adjust as necessary
            usuario.setAssociacao(associacaoRepository.findById((long) (random.nextInt(10000) + 1)).orElse(null)); // Adjust as necessary
            usuario.setTipoAcesso(EnumTipoAcesso.values()[random.nextInt(EnumTipoAcesso.values().length)]);
            usuario.setSenha(faker.internet().password());
            usuario.setSituacao(EnumUsuarioSituacao.values()[random.nextInt(EnumUsuarioSituacao.values().length)]);
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

    private void generateTokenAutenticacaos(int count) {
        IntStream.range(0, count).forEach(i -> {
            TokenAutenticacao tokenAutenticacao = new TokenAutenticacao();
            tokenAutenticacao.setUsuario(usuarioRepository.findById((long) (random.nextInt(10000) + 1)).orElse(null)); // Adjust as necessary
            tokenAutenticacao.setToken(faker.internet().uuid());
            tokenAutenticacao.setDataValidade(randomDate());
            tokenAutenticacao.setCreatedAt(randomDate());
            tokenAutenticacao.setUpdatedAt(randomDate());
            tokenAutenticacaoRepository.save(tokenAutenticacao);
        });

        System.out.println("passou token");
    }

    private void generatePagamentos(int count) {
        IntStream.range(0, count).forEach(i -> {
            Pagamento pagamento = new Pagamento();
            pagamento.setTxId(faker.internet().uuid());
            pagamento.setPixCopiaCola(faker.internet().uuid());
            pagamento.setUsuario(usuarioRepository.findById((long) (random.nextInt(10000) + 1)).orElse(null)); // Adjust as necessary
            pagamento.setTipo(EnumPagamentoTipo.values()[random.nextInt(EnumPagamentoTipo.values().length)]);
            pagamento.setValor(faker.number().numberBetween(100, 1000));
            pagamento.setMulta(faker.number().numberBetween(10, 100));
            pagamento.setDataVencimento(randomDate());
            pagamento.setDataPagamento(randomDate());
            pagamento.setSituacaoPagamento(EnumSituacaoPagamento.values()[random.nextInt(EnumSituacaoPagamento.values().length)]);
            pagamento.setCreatedAt(randomDate());
            pagamento.setUpdatedAt(randomDate());
            pagamentoRepository.save(pagamento);
        });

        System.out.println("passou pagamentos");
    }

    private void generateImagems(int count) {
        IntStream.range(0, count).forEach(i -> {
            Imagem imagem = new Imagem();
            imagem.setImagem(faker.internet().image().getBytes()); // Simulated image data
            imagem.setCreatedAt(randomDate());
            imagem.setUpdatedAt(randomDate());
            imagemRepository.save(imagem);
        });

        System.out.println("passou imagens");
    }

    private LocalDateTime randomDate() {
        return LocalDateTime.ofInstant(faker.date().past(1095, java.util.concurrent.TimeUnit.DAYS).toInstant(), ZoneId.systemDefault());
    }
}
