Feature: Gestion des pays

  En tant qu'utilisateur
  Je veux pouvoir créer, récupérer et supprimer des pays

  Scenario: Créer un pays
    Given un pays "France" avec le code3 33 et une population de 67 millions
    When je crée le pays
    Then le pays "France" doit être créé avec le code3 33 et une population de 67000000

  Scenario: Récupérer un pays par ID
    Given un pays "France" existe avec l'ID 1
    When je récupère le pays avec l'ID 1
    Then le pays récupéré doit être "France" avec le code3 33

  Scenario: Supprimer un pays
    Given un pays "France" existe avec l'ID 1
    When je supprime le pays avec l'ID 1
    Then le pays avec l'ID 1 ne doit plus exister
