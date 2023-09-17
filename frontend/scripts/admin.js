document.addEventListener('DOMContentLoaded', function () {
    const formCreateProduct = document.getElementById('createProduct-form');

    formCreateProduct.addEventListener('submit', function (event) {
        event.preventDefault();

        const productName = document.getElementById('inputProductTitel').value;
        const productDescription = document.getElementById('inputProductDescription').value;
        const productQuantity = document.getElementById('inputProductQuantity').value;
        const productType = document.getElementById('inputProductType').value;
        const productPrice = document.getElementById('inputProductPrice').value;


        const product = {
            name: productName,
            description: productDescription,
            quantity: productQuantity,
            type: productType,
            price: productPrice,
            file: true,
            active: false
        };

        console.log(product);

        $.ajax({
            url: 'http://localhost:8080/products/create',
            type: 'POST',
            contentType: 'application/json',
            headers: { "Authorization": sessionStorage.getItem("token") },
            data: JSON.stringify(product),
            success: function (response) {
                console.log('Daten erfolgreich gesendet:', response);
                alert('Daten erfolgreich gesendet!');
                location.reload();
            },
            error: function (xhr, status, error) {
                console.error('Fehler beim Senden der Daten:', error);
            }
        });
    });
});
