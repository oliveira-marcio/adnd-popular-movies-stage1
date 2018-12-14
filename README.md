# Udacity Android Developer Nanodegree - Projeto 1 (Popular Movies - Stage 1)

Este é um projeto dividido em 2 etapas. Nesta primeira, temos um app que consulta a [The Movie Database API](https://developers.themoviedb.org/3) e exibe uma grade com diversos posters de filmes que podem ser ordenados pelos mais populares ou melhores avaliados.

Ao clicar no poster de um filme, o usuário poderá ver mais detalhes do mesmo como:

- Título
- Sinópse
- Data de lançamento
- Média das avaliações dos usuários
- Total de avaliações dos usuários

O app utiliza apenas componentes nativos do framework Android como `Loader`, `HttpURLConnection`, `InputStream`, `JSONObject`, entre outros.

**OBS:** A segunda etapa do projeto pode ser acessada [aqui](https://github.com/oliveira-marcio/adnd-popular-movies-stage2).

## Instalação:
- Faça um clone do repositório
- Importe a pasta como um novo projeto no [Android Studio](https://developer.android.com/studio/)
- Crie uma chave de developer na **The Movie Database API**. Instruções [aqui](https://www.themoviedb.org/settings/api).
- Crie (ou edite) o arquivo `gradle.properties` na raiz do projeto e adicione a chave da API:
`MyTMDBApiKey="xxxxxxxxxxxxxx"`
- Configure um [emulador](https://developer.android.com/studio/run/emulator) ou conecte um [celular com USB debug ativado](https://developer.android.com/studio/run/device)
- Execute apartir do menu "Run"

## Copyright

Esse projeto foi desenvolvido por Márcio Souza de Oliveira em 31/08/2016.
