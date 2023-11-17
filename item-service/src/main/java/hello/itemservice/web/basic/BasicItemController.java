package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

//@Controller
//@RequestMapping("/basic/items")
//public class BasicItemController {
//
//    private final ItemRepository itemRepository;
//
//    @Autowired
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }
//
//}

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor //: final 변수 생성자 자동 생성
public class BasicItemController {

    private final ItemRepository itemRepository;

    /**
     * 상품 목록 페이지
     * @param model
     * @return
     */
    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "basic/items";
    }


    /**
     * 상품 상세 페이지
     * @param itemId
     * @param model
     * @return
     */
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/item";
    }

    /**
     * 상품 등록 폼
     * @return
     */
    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    /**
     * 상품 등록 처리1 (@RequestParam 사용)
     * @return
     */
//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                            @RequestParam int price,
                            @RequestParam Integer quantity,
                            Model model){

        Item item = new Item();

        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    /**
     * 상품 등록 처리2
     * @ModelAttribute 사용, model.addAttribute("item", item)
     * @param item
     * @param model
     * @return
     */
//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {
        itemRepository.save(item);

        //model.addAttribute("item", item); //자동 추가, 생략 가능
        return "basic/item";
    }


    /**
     * 상품 등록 처리3
     * 파라미터 Model model 생략, Item 객체는 item 이름 (클래스 첫글자를 소문자로 바꾼 이름)으로 자동 추가
     * @param item
     * @return
     */
//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    /**
     * 상품 등록 처리4, @ModelAttribute 자체 생략
     * @param item
     * @return
     */
    @PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }



    /**
     *  테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

}


