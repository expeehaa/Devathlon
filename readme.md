# Devathlon
Dieses git repo ist meine (expeehaa's) Abgabe zur ersten Runde des 3. Devathlons.
Es besteht aus einem Maven Projekt. Mit dem Befehl 'mvn compile package' ist es am einfachsten, eine .jar-Datei zu erstellen.
Diese befindet sich dann im neu generierten Unterordner 'target/'.

# Konfiguration
Bei erstmaligem Start des Plugins wird eine config.yml erstellt, die alle verfügbaren Optionen zur Konfiguration beinhaltet.
Innerhalb dieses Repositories befindet sich eine optionale config.yml, die die generierte Version ersetzen kann. Bei dieser handelt es sich um vorgefertigte Einstellungen, die einen etwas besseren Eindruck über das Plugin geben, als die generierte config.yml.
Alle Einstellungen können nach belieben verändert werden.

# Befehle & Permissions
Das Plugin verfügt über 2 Befehle.
'/wand <name>' gibt dem ausführenden Spieler ein Item, das unter <name> als Wand registriert ist, sofern vorhanden.
'/wandcfg' lädt die Config neu.

Insgesamt werden 3 Permissions benutzt.
wands.* - Beinhaltet alle anderen Permissions.
wands.getWandsByCommand - Für '/wand' notwendig, darf jeder Spieler ausführen.
wands.useWands - Zum Verwenden von Wands, ebenfalls für jeden Spieler zugänglich.
wands.reloadConfig - Für den Befehl zum config neu laden benötigt, nur für op.