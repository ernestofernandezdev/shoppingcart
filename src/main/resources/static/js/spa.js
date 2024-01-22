"use strict";
const API_URL = "http://localhost:8080/api/v1";

const EMPTY_FILTERS = {
	keyword: "",
	minPrice: "",
	maxPrice: ""
};


showMarket(EMPTY_FILTERS);

async function showMarket(filters) {
	let container = document.querySelector(".main");
	container.innerHTML = "";


	let nav = await getHtml("nav.html");
	if (nav != null) {
		container.innerHTML += nav;
	}


	let filtersHtml = await getHtml("filters.html");
	if (filtersHtml != null) {
		container.innerHTML += filtersHtml;
	}


	let products = await getProducts(filters);

	if (products != null) {

		for (let i = 0; i < products.length; i++) {

			let product = products[i];
			
			let productCard = await getHtml("product.html");
			if (productCard != null) {

				productCard = productCard.replace(/\${id}/g, product.id);
				productCard = productCard.replace(/\${name}/g, product.name);
				productCard = productCard.replace(/\${image-url}/g, product.imageFullName);
				productCard = productCard.replace(/\${description}/g, product.description);
				if (product.inTheCart) {
				    productCard = productCard.replace(/\${add-product-button-class}/g, "hidden");
				    productCard = productCard.replace(/\${added-product-button-class}/g, "");
				} else {
				    productCard = productCard.replace(/\${add-product-button-class}/g, "");
				    productCard = productCard.replace(/\${added-product-button-class}/g, "hidden");
				}



				container.innerHTML += productCard;

			}
		}

	}


	document.querySelectorAll(".add-product-button").forEach(button => {
		button.addEventListener("click", e => {
			let pressedButton = e.target;

			let productId = pressedButton.parentNode.getAttribute("id").split('-')[1];
			pressedButton.classList.add("hidden");
			pressedButton.parentNode.children[4].classList.remove("hidden");
			createCartLine(productId);
		})
	})


	document.querySelector(".cart-button").addEventListener("click", e => {
		showCart()
	});


	document.querySelector(".filters-form").addEventListener("submit", e => {
		e.preventDefault();
	
		let formData = new FormData(e.target);
	
		let keyword = formData.get("keyword");
		let minPrice = formData.get("minPrice");
		let maxPrice = formData.get("maxPrice");
	
		let filters = {
			keyword: keyword,
			minPrice: minPrice,
			maxPrice: maxPrice
		}
	
		showMarket(filters);
	});

}

async function showCart() {

	let container = document.querySelector(".main");
	container.innerHTML = "";


	let cartLines = await getCartLines();


	if (cartLines != null) {

		for (let i = 0; i < cartLines.length; i++) {

			let cartLine = cartLines[i];
			
			let htmlCartLine = await getHtml("cart-line.html");
			if (htmlCartLine != null) {

				htmlCartLine = htmlCartLine.replace(/\${id}/g, cartLine.id);
				htmlCartLine = htmlCartLine.replace(/\${productName}/g, cartLine.product.name);
				htmlCartLine = htmlCartLine.replace(/\${productId}/g, cartLine.product.id);
				htmlCartLine = htmlCartLine.replace(/\${amount}/g, cartLine.amount);

				container.innerHTML += htmlCartLine;

			}
		}

	}
	console.log("Agregados los productos")


	let nav = await getHtml("cart-nav.html");
	if (nav != null) {
		container.innerHTML += nav;
	}
	console.log("Agregado el nav")

	


	document.querySelector("#market-button").addEventListener("click", e => {
		showMarket(EMPTY_FILTERS)
	});


	document.querySelector("#clear-cart").addEventListener("click", e => {
		clearCart();

		showCart();
	});


	document.querySelectorAll(".line-cart-minus-button").forEach(button => {
		button.addEventListener("click", e => {
			let amountNode = e.target.parentNode.children[1];

			let amount = amountNode.textContent;
			if (amount != 1) {
				amount--;
				amountNode.innerHTML = amount;
	
				let id = e.target.parentNode.parentNode.getAttribute("id").split('-')[2];
				let productId = e.target.parentNode.parentNode.children[0].getAttribute("id").split('-')[2];
	
	
				let cartLineDto = {
					amount: amount,
					productId: productId
				}
	
				updateCartLine(cartLineDto, id);
			}
			
		})
	})


	document.querySelectorAll(".line-cart-plus-button").forEach(button => {
		button.addEventListener("click", e => {
			let amountNode = e.target.parentNode.children[1];

			let amount = amountNode.textContent;
			amount++;
			amountNode.innerHTML = amount;

			let id = e.target.parentNode.parentNode.getAttribute("id").split('-')[2];
			let productId = e.target.parentNode.parentNode.children[0].getAttribute("id").split('-')[2];


			let cartLineDto = {
				amount: amount,
				productId: productId
			}

			updateCartLine(cartLineDto, id);
			
			
		})
	})

	document.querySelectorAll(".line-cart-delete-button").forEach(button => {
		button.addEventListener("click", e => {
			let cartLineId = e.target.parentNode.parentNode.getAttribute("id").split("-")[2];

			deleteCartLine(cartLineId);

			e.target.parentNode.parentNode.remove();
		})
	})

}

async function clearCart() {

	const props = {
		method: 'DELETE'
	};

	try {
		await fetch(API_URL + "/cartlines", props);
	} catch (e) {
	}
}

async function deleteCartLine(id) {
	const props = {
		method: 'DELETE'
	};

	try {
		await fetch(API_URL + "/cartlines/" + id, props);
	} catch (e) {
	}
}

async function updateCartLine(cartLineDto, id) {
	const props = {
		method: 'PUT',
		headers: { "Content-type": "application/json" },
		body: JSON.stringify(cartLineDto)
	};

	try {
		await fetch(API_URL + "/cartlines/" + id, props);
	} catch (e) {
	}
}

async function createCartLine(productId) {
	let cartLine = {
		amount: 1,
		productId: productId
	}

	const props = {
		method: 'POST',
		headers: { "Content-type": "application/json" },
		body: JSON.stringify(cartLine)
	};
	try {
		await fetch(API_URL + "/cartlines", props);
	} catch (e) {
	}
}


async function getCartLines() {
	try {
		let response = await fetch(API_URL + "/cartlines");
		if (response.ok) {
			return await response.json();
		} else {
			return null;
		}
	} catch (error) {
		text = "<h1>Página no encontrada.</h1>";
	}
}


async function getProducts(filters) {

	let url = API_URL + "/products";
	if (filters.keyword != "" || filters.minPrice != "" || filters.maxPrice != "") {
		url += "?";

		if (filters.keyword != "") {
			url += "keyword=" + filters.keyword;
		}

		if (filters.minPrice != "") {
			url += "minPrice=" + filters.minPrice;
		}

		if (filters.maxPrice != "") {
			url += "maxPrice=" + filters.maxPrice;
		}

	}


	try {
		let response = await fetch(url);
		if (response.ok) {
			return await response.json();
		} else {
			return null;
		}
	} catch (error) {
		
	}
}

async function getHtml(link) {
	let text = "";
	try {
		let response = await fetch(link);
		text = await response.text();
	} catch (error) {
		return null;
	}
	return text;
}
