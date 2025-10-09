# ESTRUTURA DE PASTAS

devconnect/
├── main/
│ ├── routes/
│ │ ├── controller/
│ │ └── dto/
│ ├── services/
│ │ └── featureX/
│ └── repository/
│ ├── models/
│ └── repositories/
└── test/

# GIT FLOW

Git flow é um padrão adotado pelo projeto para que o fluxo do git fique de forma uniforme.

### Padrão de branches

main: Aquilo que já é versão final e está estavél
develop: tudo aquilo que ira para review
feature/nome_feature: nova funcionalidade a ser desevolvidada
task/nome_task: tarefa que será desenvolvida para a feature
bugfix/nome_bugfix: correção de bug (nao usar para branchs de task)

#### fluxo:

**Caso não exista uma branch de feature:**

```GIT
git checkcout main
git pull
git checkout -b feature/nome_feature
git checkout -b task/nome_task
```

> **OBS:** Caso a feature nao tenha tasks, criar a branch de feature e depois a de task com o mesmo nome.

**Caso exista uma branch de feature:**

```GIT
git checkcout feature/nome_feature
git pull
git checkout -b task/nome_task
```

### Padrão de commits

feat: message (Após ter criado tudo que foi solicitado na task, se for de fato uma nova funcionalidade)

tests: message (Após terminar os testes)

chore: message (Qualquer alteração antes ou depois de
finalizar a feature)

bugfix: message (Resolve um bug daquela branch)

**Exemplo:**

```GIT
git commit -m 'feat': create initial project setup

```
