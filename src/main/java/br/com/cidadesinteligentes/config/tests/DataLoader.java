package br.com.cidadesinteligentes.config.tests;

import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.model.Cargo;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.repository.ICargoRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.cidadao.model.Cidadao;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Perfil;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.repository.IPerfilRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.model.Permissao;
import br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.repository.IPermissaoRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.pessoa.model.Pessoa;
import br.com.cidadesinteligentes.modules.core.gestaousuario.pessoa.repository.IPessoaRepository;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.enums.StatusUsuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.Usuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.repository.IUsuarioRepository;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servidorpublico.model.PublicServantProfile;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Class for tests and initial data loading.
 */
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    // Injeções
    private final IPermissaoRepository permissaoRepository;
    private final ICargoRepository cargoRepository;
    private final IPessoaRepository pessoaRepository;
    private final IUsuarioRepository usuarioRepository;
    private final IPerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String SENHA_PADRAO = "123456";

    @Override @Transactional
    public void run(String... args) throws Exception {

        // 1. VERIFICAÇÃO DE DUPLICAÇÃO DE USUÁRIOS
        if (usuarioRepository.count() > 0) {
            System.out.println("Dados iniciais (usuários/cargos) já carregados. Pulando.");
            return;
        }

        System.out.println("--- 1. CARREGANDO PERMISSÕES ---");
        // 2. CRIAÇÃO E SALVAMENTO DE PERMISSÕES
        Set<Permissao> allPermissions = loadPermissions();

        // 3. CRIAÇÃO DE CARGOS COM PERMISSÕES
        System.out.println("--- 2. CARREGANDO CARGOS ---");

        // Cargo Administrador: Acesso Total
        Set<Permissao> adminPerms = new HashSet<>();
        Cargo adminRole = createRole("ADMINISTRADOR", "Acesso total ao sistema", adminPerms);

        // Cargo Servidor Público: Visualização e Revisão
        Set<Permissao> servantPerms = new HashSet<>();
        Cargo publicServantRole = createRole("SERVIDOR_PUBLICO", "Revisão e emissão de licenças", servantPerms);

        // Cargo Cidadão (Apenas para fins de Perfil, terá permissão de SUBMIT_LICENSE_REQUEST)
        Set<Permissao> citizenPerms = allPermissions.stream()
                .filter(p -> p.getNome().equals("SUBMIT_LICENSE_REQUEST"))
                .collect(Collectors.toSet());
        Cargo citizenRole = createRole("CIDADAO", "Criação de novas requisições", citizenPerms);


        // 4. CRIAÇÃO DE USUÁRIO COM MÚLTIPLOS PERFIS
        System.out.println("--- 3. CARREGANDO USUÁRIO COM 3 PERFIS ---");
        createMultiProfileUser(adminRole, publicServantRole, citizenRole);

        System.out.println("\n✅ DADOS INICIAIS CARREGADOS COM SUCESSO!");
        System.out.println("   USUÁRIO ADMIN DE TESTE: admin_test | Senha: " + SENHA_PADRAO);
    }

    // --- MÉTODOS AUXILIARES ---

    // ⬇️ MÉTODO CENTRAL PARA CRIAR O USUÁRIO COM 3 PERFIS ⬇️
    private void createMultiProfileUser(Cargo adminRole, Cargo publicServantRole, Cargo citizenRole) {

        // 1. Criação da Pessoa Base (única)
        Pessoa pessoaAdmin = createPessoa(
                "11122233344",
                "Admin Múltiplos Perfis",
                LocalDate.of(1990, 1, 1),
                "MASCULINO"
        );

        // 2. Criação do Usuário (único)
        Usuario usuario = createUsuario(
                "admin@cidades.com",
                "77988776655",
                "admin_test",
                passwordEncoder.encode(SENHA_PADRAO),
                pessoaAdmin
        );

        // --- 3. Criação dos 3 Perfis Concretos ---
        List<Perfil> todosPerfis = new ArrayList<>();

        // A) PERFIL 1: ADMINISTRADOR (Usando a classe Cidadao para teste)
        Cidadao perfilAdmin = new Cidadao();
        perfilAdmin.setTipo("ADMIN");
        perfilAdmin.setImagemUrl("url/admin");
        perfilAdmin.setUsuario(usuario);
        perfilAdmin.setCargo(adminRole);
        perfilAdmin = (Cidadao) perfilRepository.save(perfilAdmin);
        todosPerfis.add(perfilAdmin);
        System.out.println("   - Perfil ADMIN criado.");

        // B) PERFIL 2: SERVIDOR PÚBLICO (Usando a classe PublicServantProfile)
        PublicServantProfile perfilServidor = new PublicServantProfile();
        perfilServidor.setTipo("PUBLIC_SERVANT");
        perfilServidor.setImagemUrl("url/servidor");
        perfilServidor.setUsuario(usuario);
        perfilServidor.setCargo(publicServantRole);
        perfilServidor.setEmployeeId("EMP777");
        // Nota: O PublicServantProfile tem a coleção 'functions'. Se ela não for inicializada
        // na entidade PublicServantProfile, pode haver uma falha no save.
        perfilServidor = (PublicServantProfile) perfilRepository.save(perfilServidor);
        todosPerfis.add(perfilServidor);
        System.out.println("   - Perfil SERVIDOR_PUBLICO criado.");

        // C) PERFIL 3: CIDADÃO COMUM (Usando a classe Cidadao)
        Cidadao perfilCidadao = new Cidadao();
        perfilCidadao.setTipo("CIDADAO");
        perfilCidadao.setImagemUrl("url/cidadao");
        perfilCidadao.setUsuario(usuario);
        perfilCidadao.setCargo(citizenRole);
        perfilCidadao = (Cidadao) perfilRepository.save(perfilCidadao);
        todosPerfis.add(perfilCidadao);
        System.out.println("   - Perfil CIDADÃO criado.");


        // --- 4. Associação Final ao Usuário ---

        // Associa a lista completa de perfis (3 perfis)
        usuario.setPerfis(todosPerfis);

        // Define o primeiro perfil (ADMIN) como o ativo para login inicial
        usuario.setTipoAtivo(perfilAdmin);

        usuarioRepository.save(usuario);
    }


    private Set<Permissao> loadPermissions() {
        List<String> requiredPermissions = Arrays.asList(
                "REGISTER_CITIZEN", "CREATE_PUBLIC_SERVANT", "SUBMIT_LICENSE_REQUEST",
                "VIEW_ALL_LICENSES", "REVIEW_LICENSE_REQUEST", "ISSUE_LICENSE",
                "REJECT_LICENSE_REQUEST", "MANAGE_ROLES", "DEACTIVATE_USER", "VIEW_SYSTEM_LOGS"
        );

        Set<Permissao> createdPermissions = new HashSet<>();

        requiredPermissions.forEach(nome -> {
            Permissao permission = permissaoRepository.findByNome(nome)
                    .orElseGet(() -> {
                        Permissao p = new Permissao();
                        p.setNome(nome);
                        System.out.println("Permissão criada: " + nome);
                        return permissaoRepository.save(p);
                    });
            createdPermissions.add(permission);
        });
        return createdPermissions;
    }

    private Cargo createRole(String nome, String descricao, Set<Permissao> permissoes) {
        Cargo cargo = cargoRepository.findByNome(nome)
                .orElseGet(() -> {
                    Cargo novoCargo = new Cargo();
                    novoCargo.setNome(nome);
                    novoCargo.setDescricao(descricao);

                    // ⬇️ GARANTINDO COLEÇÕES MUTÁVEIS (Permissões e Perfis)
                    if (Objects.isNull(novoCargo.getPermissoes())) {
                        novoCargo.setPermissoes(new HashSet<>());
                    }
                    if (Objects.isNull(novoCargo.getPerfis())) {
                        novoCargo.setPerfis(new ArrayList<>());
                    }
                    return novoCargo;
                });

        // Limpa e adiciona as permissões de forma segura (para o setManyToMany)
        cargo.getPermissoes().clear();
        cargo.getPermissoes().addAll(permissoes);

        return cargoRepository.save(cargo);
    }

    private Pessoa createPessoa(String cpf, String nomeCompleto, LocalDate dataNascimento, String genero) {
        Pessoa pessoa = new Pessoa();
        pessoa.setCpf(cpf);
        pessoa.setNomeCompleto(nomeCompleto);
        pessoa.setDataNascimento(dataNascimento);
        pessoa.setGenero(genero);
        return pessoaRepository.save(pessoa);
    }

    private Usuario createUsuario(String email, String telefone, String nomeUsuario, String senhaCriptografada, Pessoa pessoa) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setTelefone(telefone);
        usuario.setNomeUsuario(nomeUsuario);
        usuario.setSenha(senhaCriptografada);
        usuario.setStatus(StatusUsuario.ATIVO);
        usuario.setPessoa(pessoa);
        return usuarioRepository.save(usuario);
    }
}