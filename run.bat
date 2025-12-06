## 📁 **run.bat**
```batch
@echo off
cls
echo ========================================
echo   Systeme de Livraison
echo   Compilation et Execution
echo ========================================
echo.

echo [1/2] Compilation en cours...
echo.

javac model\*.java data\*.java utils\*.java ui\*.java

if %ERRORLEVEL% EQU 0 (
    echo.
    echo [OK] Compilation reussie!
    echo.
    echo [2/2] Lancement de l'application...
    echo.
    echo ========================================
    echo.
    java ui.MainApp
) else (
    echo.
    echo [ERREUR] La compilation a echoue!
    echo.
    pause
)
```

---

## ✅ **Tous les fichiers sont maintenant complets !**

Pour utiliser le projet :

1. **Créez la structure** des dossiers
2. **Copiez chaque code** dans le fichier correspondant
3. **Exécutez** `run.bat` dans le dossier Frontend

L'application démarrera avec la page d'accueil où vous pourrez configurer votre grille ! 🚀