package thespeace.itemservice.web.basic;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thespeace.itemservice.domain.item.Item;
import thespeace.itemservice.repository.ItemRepository;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    //같은 url인데 http method로 기능을 구분.

    /**
     * <h2>상품 등록 처리 - @RequestParam</h2>
     * 요청 파라미터 형식을 처리
     * <ul>
     *     <li>먼저 `@RequestParam String itemName` : itemName 요청 파라미터 데이터를 해당 변수에 받는다</li>
     *     <li>`Item` 객체를 생성하고 `itemRepository`를 통해서 저장한다.</li>
     *     <li>저장된 `item`을 모델에 담아서 뷰에 전달한다.</li>
     * </ul>
     */
    //@PostMapping("/add") //이전 코드의 매핑 주석처리!
    public String addItemV1(@RequestParam("itemName") String itemName,
                       @RequestParam("price") int price,
                       @RequestParam("quantity") Integer quantity,
                       Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    /**
     * <h2>상품 등록 처리 - @ModelAttribute</h2>
     * {@code @ModelAttribute}를 사용해서 {@code @RequestParam}으로 변수를 하나하나 받아서 {@code Item}을 생성하는 과정을 생략 가능.
     * <ul>@ModelAttribute - 요청 파라미터 처리
     *     <li>@ModelAttribute 는 Item 객체를 생성하고, 요청 파라미터의 값을 프로퍼티 접근법(setXxx)으로 입력해준다.</li>
     * </ul>
     * <ul>@ModelAttribute - Model 추가
     *     <li>@ModelAttribute 는 중요한 한가지 기능이 더 있는데, 바로 모델(Model)에 @ModelAttribute 로 지정한 객체를
     *         자동으로 넣어준다. 지금 코드를 보면 model.addAttribute("item", item) 가 주석처리 되어 있어도 잘 동작하는
     *         것을 확인할 수 있다.</li>
     *     <li>모델에 데이터를 담을 때는 이름이 필요하다. 이름은 @ModelAttribute 에 지정한 name(value) 속성을 사용한다.
     *         만약 다음과 같이 @ModelAttribute 의 이름을 다르게 지정하면 다른 이름으로 모델에 포함된다.</li>
     * </ul>
     * <blockquote><pre>
     *     {@code @ModelAttribute("hello") Item item} ->이름을 hello 로 지정
     *     {@code model.addAttribute("hello", item);} -> 모델에 hello 이름으로 저장
     * </pre></blockquote>
     */
    //@PostMapping("/add") //이전 코드의 매핑 주석처리!
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {
        itemRepository.save(item);
//        model.addAttribute("item", item); //자동 추가, 생략 가능.

        return "basic/item";
    }

    /**
     * <h2>상품 등록 처리 - ModelAttribute 이름 생략</h2>
     * {@code @ModelAttribute}의 이름을 생략하면 모델에 저장될 때 클래스명을 사용한다. 이때 클래스의 첫글자만 소문자로 변경해서 등록한다.
     * <ul> 예) {@code @ModelAttribute} 클래스명 모델에 자동 추가되는 이름
     *     <li>{@code Item item}</li>
     *     <li>{@code HelloWorld helloWorld}</li>
     * </ul>
     */
    //@PostMapping("/add") //이전 코드의 매핑 주석처리!
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);
//        model.addAttribute("item", item); //자동 추가, 생략 가능.

        return "basic/item";
    }

    /**
     * <h2>상품 등록 처리 - ModelAttribute 전체 생략</h2>
     * {@code @ModelAttribute} 자체도 생략가능하다. 기본 타입이 아니라 우리가 만든 임의의
     * 대상 객체는 자동으로 @ModelAttribute가 적용되면서 모델에 자동 등록된다. 나머지 사항은 기존과 동일.
     */
    @PostMapping("/add")
    public String addItemV4( Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
