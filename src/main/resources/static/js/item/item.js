function addProduct(productId, productName, productPrice,imageId) {
    let price = 0
    let orderPayloadModel = {
      productId: '',
      productName: '',
      quantity: '',
      price:'',
      totalPrice: '',
      image:''
    }
    let orderPayloadStr = sessionStorage.getItem('orderPayload');
    let orderPayload = null;
    if (orderPayloadStr != null) {
        let orderPayloadDecode = atob(orderPayloadStr);
        orderPayload = JSON.parse(orderPayloadDecode);
    }
    const quantity = document.getElementById(productId).value
    const productNameStr = document.getElementById(productName).value
    const image = document.getElementById(imageId).value
    price = document.getElementById(productPrice).value
    let checkProduct = false

    if (orderPayloadStr != null) {
      for (var i = 0; i < orderPayload.length; i++) {
        checkProduct = true
          //เพิ่มสินค้าที่เคยใส่ตะกร้า
        if (orderPayload[i].productId == productId) {
          checkProduct = false
          orderPayloadModel = orderPayload[i]
          orderPayload.splice(i, 1);
          orderPayloadModel.productId = productId
          orderPayloadModel.productName = productNameStr
          orderPayloadModel.quantity = parseInt(orderPayloadModel.quantity)
            + parseInt(quantity)
          orderPayloadModel.price = price  
          orderPayloadModel.totalPrice = parseInt(orderPayloadModel.totalPrice)
            + (quantity * price)
          orderPayloadModel.image = image

          if(parseInt(orderPayloadModel.quantity) < 999999 ){
              orderPayload.push(orderPayloadModel)
          }else{
              orderPayloadModel.quantity = 999999
              orderPayload.push(orderPayloadModel)
              $("#limitItemModal").modal();
          }
          break
        }
      }
      //เพิ่มสินค้าที่ยังไม่เคยใส่ตะกร้า
      if (checkProduct) {
        orderPayloadModel.productId = productId
        orderPayloadModel.productName = productNameStr
        orderPayloadModel.quantity = parseInt(quantity)
        orderPayloadModel.price = price  
        orderPayloadModel.totalPrice = (quantity * price)
        orderPayloadModel.image = image
        orderPayload.push(orderPayloadModel)
      }
    } else { // ตะกร้าสินค้าว่างจะเพิ่มชิ้นแรกตรงนี้
      orderPayload = []
      orderPayloadModel.productId = productId
      orderPayloadModel.productName = productNameStr
      orderPayloadModel.quantity = parseInt(quantity)
      orderPayloadModel.price = price  
      orderPayloadModel.totalPrice = (quantity * price)
      orderPayloadModel.image = image
      orderPayload.push(orderPayloadModel)
    }
    document.getElementById(productId).value = 1
    orderPayload.sort((a,b)=> (a.productId > b.productId ? 1 : -1))

    sessionStorage.setItem('orderPayload', btoa(JSON.stringify(orderPayload)));

    getCartSession();
    openNavShadow();
    openNav();
    
  }
  
  function productPlus(productId) {
    let total = document.getElementById(productId).value
    let totalInt = 1
    if(parseInt(total) < 999999 ) {
        totalInt = parseInt(total) + 1
    }else{
        totalInt = 999999
        $("#limitItemModal").modal();
    }
    document.getElementById(productId).value = totalInt.toString()
  }
  
  function productMinus(productId) {
    var total = document.getElementById(productId).value
    var totalInt = 1
    if (parseInt(total) > 1) {
      totalInt = parseInt(total) - 1
    }
    document.getElementById(productId).value = totalInt.toString()
  }

  function checkBlankAndZero(productId){
      var total = document.getElementById(productId).value
      var totalInt = 1;
      if (Number(total) < 1) {
          totalInt = 1;
      }else{
          totalInt = Number(total);
      }
      document.getElementById(productId).value = totalInt.toString()
  }
  