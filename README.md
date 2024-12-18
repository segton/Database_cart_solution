# Cartcompare
# Inas: Add Fragment & Item Details
---
## What's New in v5: 
  - _Restructured my Add Fragment_
  - _centralized data management at: ItemRepository.kt_
  - _completed all categories_

---

## Overview of what's done
    
### **1. Add Fragment:** 

![image](https://github.com/user-attachments/assets/f219892b-8fa6-4e74-9e1a-4f366b4888ea)

- Add Fragment's xml layout consists of sub-layouts: item_card.xml (refer image)
- if "+" button is clicked, notificiation "apples added to cart" is shown
- if the card of Apples/Mangoes/etc is selected, app navigates to Item Details Fragment

_p/s: item_card.xml is **just a template**_, but my code generates copies of the template and setup their details based on data from _ItemRepository.kt_


  
### **2. Item Details:** 

![image](https://github.com/user-attachments/assets/2bb2b421-708a-4895-822a-8b397b47f80e)

- this was supposed to be Sean's part but I accidentally overdid this woopsie, didn't realise it ;-;
- the " -  0  + " feature doesn't function yet.
- the sort by price feature works :D

### **3. ItemRepository.kt** 

a. **ItemRepository.kt** is where details for all item objects is coded

- If we wanna edit, add or delete data just do it here. 

![image](https://github.com/user-attachments/assets/d73f59db-9211-45c8-b208-8f24abfebf5f)


b. **Item.kt**  is just a blueprint for the data in (a) ItemRepository.kt 
  
---

## What's needed to be done next?

1. use ViewModel to store data of cart item details & quantity.
   
   - just chatgpt about it.
   - discuss other solutions than ViewModel if better.
     
2. if (1) is completed, then we can make the " -  0  + " and "+" feature work. 
3. Vesim & Moha can start designing UI of Cart Fragment
   
_p/s: 1 & 2 seems like a fair task so maybe Sean will do this part_

---
## Basic Tips on How To Start

Each fragment comes along with a UI design: xml file.

All fragments will need some sorta binding process, to link code with its xml. so just follow these structures:

When making your fragment.kt, you'll need 3 of these basic setups: onCreateView(), onViewCreated(), onVeiwDestroyed()

**1. onCreateView():**

this is for binding your code.kt with ur design.xml file. here is example code to start in AFragment.kt, just copy but put your xml file name instead
  
        //binding with your xml layout
        private var _binding: FragmentABinding? = null  //if xml file name is fragment_A.xml, write it as FragmentABinding
        private val binding get() = _binding!!
    
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            _binding = FragmentABinding.inflate(inflater, container, false) //change here also !!
            return binding.root 
       
**2. onViewCreated():**

this is where you code all UI interactions here. you can now call your xml elements using "binding.yourElementId" 
     
      override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

          //example
          binding.YourButton.setOnClickListener{
              //bla bla bla
          }
       }
       
**3. onViewDestroyed():**

for cleaning up any references or resources that might cause memory leaks.
        
        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null // Prevent memory leaks
        }


_p/s: try to imitate the Figma UI design Moha made. You can just copy any elements or color codes from my xml layouts._

see prototype:

_https://www.figma.com/design/DePxUDJZNDtdEWumMWYphj/MAD-project?node-id=1-174&node-type=frame&t=3YIcnxKiPKPT7zzS-0_

---
## Extra Features for the Future

- search bar in add fragment
- sort items in A-Z order by default
- show list of categories first instead of entering fruit category by default, just like in prototype.

## Tips for Team Collaboration

- **Pull from main frequently** to make sure you’re not missing any updates.
- **Communicate regularly**: If you’re making big changes or finishing your work, let the team know.
- **Ask for help**: If you get stuck, reach out. It’s better to solve issues together than to struggle alone.

Let’s work together to make this project a success!
