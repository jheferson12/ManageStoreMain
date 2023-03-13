package co.edu.umanizales.manage_store.controller;

import co.edu.umanizales.manage_store.controller.dto.ResponseDTO;
import co.edu.umanizales.manage_store.controller.dto.SaleByStoreDTO;
import co.edu.umanizales.manage_store.controller.dto.SaleDTO;
import co.edu.umanizales.manage_store.model.Sale;
import co.edu.umanizales.manage_store.model.Seller;
import co.edu.umanizales.manage_store.model.Store;
import co.edu.umanizales.manage_store.service.SaleService;
import co.edu.umanizales.manage_store.service.SellerService;
import co.edu.umanizales.manage_store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "sale")
public class SaleController {




    @Autowired
    private SaleService saleService;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private StoreService storeService;



    private Store store;
    public class SaleService {
        private List<Sale> sales;
        private List<Store>SaleByStoreDTO;
        public void addStoreAverage(Store store){this.SaleByStoreDTO.add(store);}

// En este codigo tenemos las ventas por tienda donde cree una clase que se llame SaleByStoreDTO
    public List<Store> SaleByStoreDTO(List<Store>stores)
    {
        //Despues cree en la clase DTO una variable que se llame int quantity y lo inicialice en 0
        int quantity=0;
        //Despues cree una lista tipoSaleByStoreDTO en el SaleService
        List<Store>=SaleByStoreDTO new ArrayList<>();
        //Despues lo que hice es usando un for para que vea como tal las tienas de la clase
        for(Store store:stores){
            if(SaleByStoreDTO(store.getCode())>quantity)
            {
                SaleByStoreDTO.add(store);
            }
        }
        //Y despues retorno el valor de la clase DTO es decir el valor que tiene la clase que devuelva el valor
        return SaleByStoreDTO;



    }
    @GetMapping
    public ResponseEntity<ResponseDTO> getSales(){
        return new ResponseEntity<>(
                new ResponseDTO(200,
                        saleService.getSales(),
                null),
                HttpStatus.OK);
    }
    @GetMapping(path="/total")
    public ResponseEntity<ResponseDTO> getTotalSales(){
        return new ResponseEntity<>(
                new ResponseDTO(200, saleService.getTotalSales(),null),HttpStatus.OK);
    }

    @GetMapping(path="/total/{code}")
    public ResponseEntity<ResponseDTO> getTotalSalesBySeller(
            @PathVariable String code
    ){
        return new ResponseEntity<>(
                new ResponseDTO(200, saleService.getTotalSalesBySeller(code),null),HttpStatus.OK);
    }
    @GetMapping(path="/totalStore/{code}")
    public ResponseEntity<ResponseDTO> getTotalSalesByStore(
            @PathVariable String code
    ){
        return new ResponseEntity<>(
                new ResponseDTO(200, saleService.getTotalSalesByStore(code),null),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createSale(@RequestBody
                                                  SaleDTO saleDTO){
        Seller findSeller = sellerService.getSellerById(saleDTO.getSellerId());
        if( findSeller == null){
            return new ResponseEntity<>(new ResponseDTO(409,
                    "El vendedor ingresado no existe",null),
                    HttpStatus.BAD_REQUEST);
        }
        Store findStore = storeService.getStoreById(saleDTO.getStoreId());
        if( findStore == null){
            return new ResponseEntity<>(new ResponseDTO(409,
                    "La sucursal ingresada no existe",null),
                    HttpStatus.BAD_REQUEST);
        }
        saleService.addSale(new Sale(findStore,findSeller,
                saleDTO.getQuantity()));
        return new ResponseEntity<>(new ResponseDTO(200,
                "Venta adicionada",null),
                HttpStatus.OK);
    }

    @GetMapping(path = "/bestSeller")
    public ResponseEntity<ResponseDTO> getBestSeller(){
        return new ResponseEntity<>( new ResponseDTO(
                200, (saleService.getBestSeller(sellerService.getSellers())),null),HttpStatus.OK);
    }
    @GetMapping(path = "/bestStore")
    public ResponseEntity<ResponseDTO> getBestStore(){
        return new ResponseEntity<>( new ResponseDTO(
                200, (saleService.getBestStore(storeService.getStores())),null),HttpStatus.OK);
    }
    @GetMapping(path = "/averagesalebyseller")
    public ResponseEntity<ResponseDTO> getAverageSaleBySeller(){
        int average=saleService.getTotalSales();
        if(average==0){
            return new ResponseEntity<>(new ResponseDTO(409,
                    "no existen ventas,por lo tanto no se puede realizar a cabo un promedio  ",null),
                    HttpStatus.BAD_REQUEST);


        }
        else {
            return new ResponseEntity<>( new ResponseDTO(
                    200, saleService.getTotalSales()/(float)sellerService.getSellers().size(),
                    null),HttpStatus.OK);
        }

    }
    @GetMapping(path = "/averagesalebystore")
    public ResponseEntity<ResponseDTO> getAverageSaleByStore() {
        int average = saleService.getTotalSales();
        int quantitystore=storeService.getStores().size();
        if (average == 0) {
            return new ResponseEntity<>(new ResponseDTO(409,
                    "no existen ventas,por lo tanto no se puede realizar a cabo un promedio  ", null),
                    HttpStatus.BAD_REQUEST);


        } else {
            return new ResponseEntity<>(new ResponseDTO(
                    200,average/quantitystore, null), HttpStatus.OK);
        }

    }


    public SaleService getSaleService() {
        return saleService;
    }
}
}
