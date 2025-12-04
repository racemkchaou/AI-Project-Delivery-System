@echo off
chcp 65001 >nul
echo ========================================
echo   SYSTEME DE LIVRAISON - AVEC FLECHES
echo ========================================
echo.
echo Compilation des fichiers...

del *.class 2>nul

echo Compilation de Position.java...
javac Position.java

echo Compilation de GridData.java...
javac GridData.java

echo Compilation de WorkingDeliveryApp.java...
javac WorkingDeliveryApp.java

if errorlevel 1 (
    echo.
    echo ERREUR de compilation!
    pause
    exit /b 1
)

echo.
echo ========================================
echo   LANCEMENT...
echo ========================================
echo.
echo NOUVELLES FONCTIONNALITES:
echo 1. Fleches bleues sur chaque segment du chemin
echo 2. Coûts affiches sur chaque segment de la grille
echo 3. Coûts colores (0=vert,1=bleu,2=orange,3=rouge,4=violet)
echo 4. Bouton "Nouveaux Coûts" pour regenerer
echo.
echo Instructions:
echo 1. Cliquez "Visualiser" pour voir le chemin avec fleches
echo 2. Cliquez "Nouveaux Coûts" pour changer les nombres
echo 3. Observez les coûts sur chaque segment de la grille
echo.

java WorkingDeliveryApp

pause