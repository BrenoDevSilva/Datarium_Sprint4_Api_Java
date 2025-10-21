# Relatório de Impacto à Proteção de Dados (RIPD)
## Datarium Sprint 4 - API Java

### 1. Identificação
- **Projeto**: Datarium Investment API
- **Tecnologia**: Java Spring Boot
- **Responsável**: Anna Yagyu 〡 Rm 550360 
Breno Silva 〡 Rm 99275 
Danilo Urze 〡 Rm 99465 
Gabriel Pacheco 〡Rm 550191


### 2. Tratamento de Dados - Login/Cadastro

| Dado Coletado | Finalidade | Base Legal | Armazenamento |
|---------------|------------|------------|---------------|
| Email | Autenticação | Consentimento | PostgreSQL |
| Senha | Autenticação | Consentimento | Hash BCrypt |
| Nome | Identificação | Consentimento | PostgreSQL |
| CPF | Compliance | Consentimento | Criptografado |

### 3. Controles de Segurança Implementados

#### Pipeline DevSecOps
- **SAST**: CodeQL + SpotBugs + Semgrep
- **SCA**: OWASP Dependency Check + Snyk
- **DAST**: OWASP ZAP
- **Quality Gates**: Bloqueio automático em vulnerabilidades críticas

#### Controles no Código
- **Validação**: Bean Validation (@Valid)
- **Sanitização**: Input filtering
- **Autenticação**: Spring Security + JWT
- **Criptografia**: BCrypt para senhas
- **RBAC**: @PreAuthorize roles

### 4. Evidências
- GitHub Actions: workflows executados
- Security tab: alertas e scanning
- Artifacts: relatórios gerados

### 5. Conformidade LGPD
- Consentimento registrado
- Logs de auditoria
- Criptografia de dados sensíveis
- Controle de acesso por roles
- API para exclusão de dados