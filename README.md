# Gerenciador de Produtos (CRUD Completo)

Este projeto consiste em uma solução Full-Stack simplificada para o gerenciamento de estoque de produtos. Ele é composto por uma **API RESTful** robusta desenvolvida em Java com Spring Boot e um **Aplicativo Móvel** nativo desenvolvido em Kotlin para Android.

---

## Arquitetura do Sistema

O ecossistema foi projetado para rodar localmente com comunicação integrada através do emulador do Android Studio direcionado ao servidor local.

> [ Celular/Emulador ] 📱 ---> (HTTP / IP: 10.0.2.2:8080) ---> [ API Spring Boot ] ☕ ---> [ Banco de Dados MySQL ] 🐬

---

## ⚡ Backend: API REST (Spring Boot)

A API gerencia a persistência dos dados de forma segura, tratando regras de negócio e retornando códigos de status HTTP apropriados junto com mensagens de validação em formato texto (tratadas via `ResponseEntity`).

### 🛠️ Estrutura de Tabelas (SQL)

```sql
CREATE TABLE produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(30),
    preco DECIMAL(10,2)
);
```

### 🛣️ Endpoints Disponíveis (Documentação Swagger)

| Método | Endpoint | Parâmetros | Descrição | Status HTTP |
| :--- | :--- | :--- | :--- | :--- |
| **POST** | `/api/produto` | `@Query String nome, BigDecimal preco` | Insere um novo produto (ID automático). | `211 Created` |
| **GET** | `/api/produto` | Nenhum | Lista todos os produtos cadastrados. | `200 OK` ou `204 No Content` |
| **GET** | `/api/produto/{id}` | `@PathVariable Integer id` | Busca um produto específico pelo seu ID único. | `200 OK` ou `404 Not Found` |
| **PUT** | `/api/produto` | `@Query Integer id, String novoNome, BigDecimal novoPreco` | Altera dados de um produto existente. | `200 OK` ou `404 Not Found` |
| **DELETE**| `/api/produto` | `@Query Integer id` | Remove permanentemente um produto pelo ID. | `200 OK` or `404 Not Found` |

---

## 📱 Frontend: Aplicativo Móvel (Android Studio)

O aplicativo foi modularizado para seguir as boas práticas de desenvolvimento Android, separando as responsabilidades de navegação, interface de usuário e consumo de dados.

### 📁 Organização de Pastas (Arquitetura)

* `MenuActivity`: Funciona como o painel central (Hub de Navegação). Utiliza `Intents` explícitas para abrir as telas do CRUD.
* `CreateActivity`, `ReadActivity`, `UpdateActivity`, `DeleteActivity`: Telas específicas que gerenciam seus respectivos formulários e interações.
* `dados/Produto`: Data Class que mapeia a entidade do banco de dados.
* `dados/ProdutoApi`: Interface com mapeamento de rotas e parâmetros usando a biblioteca **Retrofit**.
* `dados/RetrofitClient`: Singleton (`object` Kotlin) centralizado que gerencia a instância do Retrofit e injeta o `ScalarsConverterFactory` para ler mensagens de texto puro do servidor.
* `dados/RetrofitExtensions`: Funções de extensão do Kotlin que eliminam códigos repetitivos (*boilerplate*), encapsulando o método `.enqueue()` do Retrofit em chamadas limpas via funções lambda.

---

## 📸 Demonstração de Funcionamento

Nesta seção estão documentadas as telas e os fluxos de sucesso e tratamento de erros do ecossistema.

### 1. Painel Principal (Menu de Navegação)
A tela inicial direciona o usuário para qualquer uma das operações do gerenciador.

<img width="303" height="577" alt="{B0F43095-17A6-4897-9131-19A0C125F15F}" src="https://github.com/user-attachments/assets/cdef9a69-1587-4ce3-85c8-892debe6f46d" />

---

### 2. Cadastrar Novo Produto (Create)
Formulário para envio de dados com tratamento de campos vazios. O ID não é solicitado pois é gerado pelo banco.

<img width="321" height="576" alt="{B168EF5A-3976-4A46-86E3-CB05941812C0}" src="https://github.com/user-attachments/assets/6536527b-2ade-4fe7-bc23-6f8f696597de" />
<br>
<img width="323" height="208" alt="{CC304870-2864-419E-A28B-E81B94B21BEF}" src="https://github.com/user-attachments/assets/7b57b882-f978-4706-9b01-8b0393e25eff" />
<br>
<img width="317" height="303" alt="{116B56E4-529C-4DD7-9FE4-71814818C287}" src="https://github.com/user-attachments/assets/5b49c1ab-99de-4640-b486-1c2179932dd5" />

---

### 3. Consulta de Itens (Read)
Permite listar todo o estoque em um painel rolável (`ScrollView`) ou buscar um item de ID específico.

<img width="310" height="578" alt="{161A366D-C21A-4584-9506-4DDE752F9D55}" src="https://github.com/user-attachments/assets/5ffba6d6-4945-4392-a894-8bbe5a5a0dd0" />

- Listagem Geral
<img width="322" height="187" alt="{03B3543F-98BE-4769-88DE-F59FDC155F77}" src="https://github.com/user-attachments/assets/d3ff3115-7a6c-496a-bd6d-aceb269725ee" />

- Listagem por Id
<img width="284" height="236" alt="{E52F017F-0080-48C1-8FCD-EF7AFDF8C3C2}" src="https://github.com/user-attachments/assets/68fb2c4d-d1a2-42f4-aa55-5138431034ea" />

---

### 4. Modificar Dados (Update)
Altera os dados de registros existentes enviando as novas informações atreladas ao ID correto.

<img width="286" height="577" alt="{99235EEF-98AD-40E8-9076-55923EC1B4BA}" src="https://github.com/user-attachments/assets/b599177a-3cc8-47b9-9de6-b2a2cb7b24b7" />
<br>
<img width="295" height="240" alt="{572B5F4B-7EF0-4A1E-8B9A-B5845BAC899B}" src="https://github.com/user-attachments/assets/474e66a1-09a8-4f09-98e3-3e6630559fc6" />
<br>
- Verificação usando a consulta
<img width="301" height="172" alt="{9BB710A7-510D-4F37-8E81-4EBAB86AF7B2}" src="https://github.com/user-attachments/assets/4a50dc54-75f9-4d06-b469-33cfb0a94528" />
<br>
<img width="302" height="213" alt="{6D926D18-547B-4DD9-BBAB-B6C834023641}" src="https://github.com/user-attachments/assets/cdab5f9f-bcb8-4567-9ea1-7fcf0166b862" />

---

### 5. Eliminar Registros e Respostas de Erro (Delete & Contramedidas)
Demonstração da remoção de itens e o sistema de contramedidas agindo: caso o usuário tente buscar, editar ou deletar um ID que não existe, a API responde com o Status `404 Not Found` e o aplicativo exibe o erro amigavelmente.

<img width="319" height="577" alt="{4060C3F4-6243-4CEC-895F-CAD095C49754}" src="https://github.com/user-attachments/assets/cfeb926d-6e40-4a43-99a5-0a96fcb4dde3" />
<br>
<img width="306" height="200" alt="{EFAABAE8-4869-42EB-8FD4-A950FD6C977A}" src="https://github.com/user-attachments/assets/c39c48f1-6587-4813-baa6-f3a93486b447" />
<br>
- Verificação usando a consulta
<img width="297" height="212" alt="{412B0667-F66D-4812-BC1F-FB6225495E6B}" src="https://github.com/user-attachments/assets/e1dda4c8-bdf2-428d-9310-4b629baa7130" />


---

## 🛠️ Como Executar o Projeto Localmente

### Pré-requisitos
1. Java JDK 17 ou superior instalado.
2. MySQL Server ativo.
3. Android Studio instalado com um Dispositivo Virtual (Emulador) configurado.

### Configuração
1. Configure o banco de dados MySQL criando um esquema de nome correspondente ao indicado na string de conexão JDBC do arquivo `GerenciadorProduto.java`.
2. Execute a aplicação Spring Boot pela sua IDE (IntelliJ/Eclipse) ou via terminal.
3. Certifique-se de que o Manifest do projeto Android contém a permissão de internet (`android.permission.INTERNET`) e a flag `android:usesCleartextTraffic="true"`.
4. Rode o aplicativo no emulador do Android Studio. A comunicação utilizará o IP padrão `10.0.2.2`.
