$.ajax({
    url: "http://localhost:8080/products/active",
    cors: true,
    headers: { "Authorization": sessionStorage.getItem("token") },
    success: function(response) { addProductstoPage(response) },
    error: function(error) { console.error(error) }
});

function addProductstoPage(products) {
    const productsContainer = $("#productsContainer");
    productsContainer.empty();

    let row;
    for (let i = 0; i < products.length; i++) {
        if (i % 3 === 0) {
            row = $(`<div class="row justify-content-center mt-3"></div>`);
            productsContainer.append(row);
        }
        $.ajax({
            url: `http://localhost:8080/files/${products[i].imageUrl}`,
            cors: true, // Eventuell ist dies nicht notwendig, abhängig von deiner Konfiguration
            headers: { "Authorization": sessionStorage.getItem("token") },
            success: function(imageUrl) {
                const productCard = createProduct(products[i], imageUrl);
                row.append(productCard);
            },
            error: function(error) {
                console.error(error);
            }
        });
    }
}

function createProduct(product) {

    //Create the card container with col-3 and mb-3 class
    const cardContainer = $("<div>", { class: "col-12 col-lg-4 col-xxl-4 d-flex justify-content-center mb-3" });
    //Create the card element
    const card = $(`<div class="card border border-light text-black p-3" style="width: 20rem;"></div>`);
    //Create the image element
    const image = $(`<img class="card-img-top border border-light rounded" height="250" src="http://localhost:8080/files/${product.imageUrl}">`);
    //Create the card body element
    const cardBody = $(`<div class="card-body border rounded mt-1"></div>`);
    //Create the name element
    const name = $(`<h5 class="card-title text-center">${product.name}</h5>`);
    //Create Dropdown
    const drop = $(`<div class="d-flex justify-content-center mb-1 mt-2"><button type="button" class="btn btn-light" style="width: 1.5rem; height: 1.5rem; padding: 0;" data-bs-toggle="collapse" href="#plus-${product.id}" role="button" aria-expanded="false" data-toggle="tooltip" data-placement="top" title="Beschreibung anzeigen">
        <i class="fas fa-caret-down"></i>
        </button></div>`);
    //Create the description element
    const description = $(`<p class="card-text">${product.description}</p>`);
    //Create Footer
    const cardFooter = $(`<div class="card-footer border border-1 border-top-1 d-flex justify-content-between">`)
//Create the price element
const price = $(`<p class="card-text">${product.price} €</p>`);
//Create add Product
const addProduct = $(`<button type="button" class="btn btn-dark" id="add-to-cart-button" data-product-id="${product.id}" style="width: 3rem; height: 3rem; padding: 0;" role="button" data-toggle="tooltip" data-placement="top" title="Produkt zum Warenkorb hinzufügen">add</button>
`);
    //Append the elements to the card
    cardBody.append(name, drop, description);
    //Append the image and card body to the card
    card.append(image, cardBody, cardFooter);
    //Append the card to the card container
    cardContainer.append(card);
    //Append the footer to the card
    cardFooter.append(price, addProduct)

    //Return the card containeraa
    return cardContainer;
}


function addProductToCart(product, quantity) {
    const data = {
        productId: product.id,
        quantity: quantity
    }

    $.post({
        url: "http://localhost:8080/positions",
        headers: { "Authorization": sessionStorage.getItem("token") },
        cors: true,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: console.log,
        error: console.error
    });
}
