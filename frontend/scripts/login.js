function login() {
    $.post({
        url: "http://localhost:8080/login",
        contentType: "application/json",
        data: JSON.stringify({
            username: $("#usernameInput").val(),
            password: $("#passwordInput").val()
        }),
        success: function (data) {
            var timestamp = new Date().toISOString(); // Aktuellen Zeitpunkt erfassen
            sessionStorage.setItem("token", data);
            sessionStorage.setItem("loginTimestamp", timestamp); // Timestamp speichern
            location.href = "index.html";
            console.log("Eingeloggt");
        },
        error: console.error
    });
}

$(document).on('shown.bs.modal', '#navbarModal', function () {
    document.getElementById("loginButton").addEventListener("click", login);
});


function checkTokenValidity() {
    var loginTimestamp = sessionStorage.getItem("loginTimestamp");
    if (loginTimestamp) {
        var currentTime = new Date().getTime();
        var loginTime = new Date(loginTimestamp).getTime();
        var validityDuration = 3600 * 1000; // Gültigkeitsdauer des Tokens in Millisekunden (hier 1 Stunde)

        if (currentTime - loginTime >= validityDuration) {
            // Token ist abgelaufen
            sessionStorage.removeItem("token");
            sessionStorage.removeItem("loginTimestamp");
            console.log("Token ist abgelaufen");

            // Benutzer abmelden und zur Indexseite zurückleiten
            location.href = "index.html";

            // Benachrichtigung anzeigen
            alert("Ihre Sitzung ist abgelaufen. Sie wurden abgemeldet.");

            // Hier kannst du ggf. weitere Aktionen ausführen, die nach dem Abmelden erforderlich sind
        }
    }
}
function checkAdminStatus() {
    // Token aus der Session holen
    const token = sessionStorage.getItem('token');
    // Überprüfen, ob ein Token vorhanden ist
    if (token) {
      // Token aufteilen und den Payload extrahieren
    const tokenParts = token.split('.');
    const payloadBase64 = tokenParts[1];
    const payload = JSON.parse(atob(payloadBase64));
      // Überprüfen, ob der Benutzer ein Administrator ist
    const isAdmin = payload.admin;
    return isAdmin;
    } else {
      return false; // Kein Token gefunden, Benutzer ist kein Administrator
    }
}
