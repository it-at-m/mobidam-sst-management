const { description } = require('../../package')

module.exports = {
  base: '/mobidam-sst-management/',
  /**
   * Ref：https://v1.vuepress.vuejs.org/config/#title
   */
  title: 'MobidaM',
  /**
   * Ref：https://v1.vuepress.vuejs.org/config/#description
   */
  description: description,

  /**
   * Extra tags to be injected to the page HTML `<head>`
   *
   * ref：https://v1.vuepress.vuejs.org/config/#head
   */
  head: [
    ['meta', { name: 'theme-color', content: '#333333' }],
    ['meta', { name: 'apple-mobile-web-app-capable', content: 'yes' }],
    ['meta', { name: 'apple-mobile-web-app-status-bar-style', content: 'black' }]
  ],

  /**
   * Theme configuration, here is the default theme configuration for VuePress.
   *
   * ref：https://v1.vuepress.vuejs.org/theme/default-theme-config.html
   */
  themeConfig: {
    repo: 'https://github.com/it-at-m/mobidam-sst-management/tree/main',
    editLinks: false,
    docsDir: '',
    editLinkText: '',
    lastUpdated: false,
    nav: [
      {
        text: 'Dokumentation',
        link: '/dokumentation/',
      },
      {
        text: 'Release-Notes',
        link: '/releasenotes/'
      },
      {
        text: 'API',
        link: '/api/'
      },
      {
        text: 'Konfiguration',
        link: '/konfiguration/'
      }
    ],
    sidebar: {
      '/dokumentation/': [
        {
          title: 'Dokumentation',
          collapsable: false,
          children: [
            ''
          ]
        }
      ],
      "/releasenotes/": [
        {
          title: "Release-Notes",
          collapsable: false
        }
      ],
      "/api/": [
        {
          title: "API",
          collapsable: false
        }
      ],
      "/konfiguration/": [
        {
          title: "Konfiguration",
          collapsable: false
        }
      ]
    }
  },

  /**
   * markdown extension
   */
  markdown: {
    lineNumbers: true,
    extendMarkdown: (md) => {
      md.use(require("markdown-it-footnote"));
    },
  },

  /**
   * Apply plugins，ref：https://v1.vuepress.vuejs.org/zh/plugin/
   */
  plugins: [
    '@vuepress/plugin-back-to-top',
    '@vuepress/plugin-medium-zoom',
    'vuepress-plugin-mermaidjs'
  ]
}
