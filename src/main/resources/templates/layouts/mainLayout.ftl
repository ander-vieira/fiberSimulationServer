<#macro layout>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>${appName}</title>

        <link rel="stylesheet" href="css/main.css" />

        <script src="https://cdn.jsdelivr.net/npm/vue@2.6.14/dist/vue.js"></script>
        <script src="/js/utils.js"></script>
    </head>
    <body>
        <h1>${appName}</h1>

        <#nested />
    </body>
    </html>
</#macro>