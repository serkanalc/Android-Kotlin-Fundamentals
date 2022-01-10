# Kullanıcı Etkileşimi Ekleyin

- [ColorMyView Projesini Oluşturun](#1)
- [ConstraintLayout Oluşturmak İçin Layout Editor Kullanın](#2)
- [TextView'e Stil Verin](#3)
- [İkinci bir TextView Ekleyin ve Constrait Ekleyin (kısıtlamalar)](#4)
- [TextView Görünümleri Zinciri Oluşturun](#5)
- [Text görünümerine ClickHandler Ekleyin](#6)
- [Temel Constrait Ekleyin](#7)
- [Buton Zinciri Ekleyin](#8)
- [Butonlara ClickHandlers Ekleyin](#9)


ColorMyViews uygulaması, Hollandalı sanatçı Piet Mondrian'dan esinlenmiştir. Siyah, beyaz, gri ve ana renklerde yalnızca dikey ve yatay çizgiler ve dikdörtgen şekiller kullanan neoplastikizm adlı bir resim stili icat etti.

<img width="800" alt="Screen Shot 2021-11-26 at 23 31 16" src="https://user-images.githubusercontent.com/80598532/143627483-057419cd-37b9-4a7b-b248-e8c644c7f4d4.png">


Resimler statik olsa da uygulamanız interaktif (etkileşimli) olacak! Uygulama, dokunulduğunda rengi değişen tıklanabilir metin görünümlerinden ve bir constraintLayout'taki buton görünümlerinden oluşur.

<img width="802" alt="Screen Shot 2021-11-26 at 23 45 14" src="https://user-images.githubusercontent.com/80598532/143633610-93832f9c-1d23-4b53-bc3b-af0df067432c.png">


### ConstraintLayout
  ConstraintLayout, alt görünümleri esnek bir şekilde konumlandırmanıza ve boyutlandırmanıza izin veren bir ViewGroup'tur. Bir ConstraintLayout, düz görünüm hiyerarşileriyle (iç içe görünüm grupları olmadan) büyük, karmaşık düzenler oluşturmanıza olanak tanır. Bir ConstraintLayout oluşturmak için, kısıtlamalar eklemek ve görünümleri sürükleyip bırakmak için Layout Editor'ü kullanabilirsiniz. XML'i düzenlemeniz gerekmez.
  
  Not:ConstraintLayout, API düzeyi 9 ve üzeri sürümlerde bulunan bir destek kitaplığı olarak mevcuttur.
  
  ### Constraints
   Constraint, iki UI öğesi arasındaki bağlantı veya hizalamadır. Her Constraint, bir görünümü başka bir görünüme, üst düzene veya görünmez bir kılavuza bağlar veya hizalar. Bir ConstraintLayout'ta, en az bir yatay ve bir dikey kısıtlama tanımlayarak bir görünümü konumlandırırsınız.
  
  ![image](https://user-images.githubusercontent.com/80598532/143634494-6f0e93b4-eaf0-48ce-98b8-0fb8a8a10dd9.png)

  ![image](https://user-images.githubusercontent.com/80598532/143634735-dd283226-c8f0-4ec1-995b-9f2bdd1ade4a.png)
 Horizontal constraint: B, A'nın sağında kalacak şekilde sınırlandırılmıştır. (Bitmiş bir uygulamada, B'nin bu yatay kısıtlamaya ek olarak en az bir dikey kısıtlamaya ihtiyacı olacaktır.)
 
  ![image](https://user-images.githubusercontent.com/80598532/143634927-3b987382-445e-4b66-8ddb-735fe7ff64c5.png) Vertical constraint: C, A'nın altında kalacak şekilde sınırlandırılmıştır. (Bitmiş bir uygulamada, C'nin bu dikey kısıtlamaya ek olarak en az bir yatay kısıtlamaya ihtiyacı olacaktır.)
    

## <a name="1"></a>ColorMyView Projesini Oluşturun
    
    1. Henüz açık değilse Android Studio'yu açın ve aşağıdaki parametrelerle yeni bir proje oluşturun:
    
| Attribute | Value |
|---|---|
| Template | Telefon ve Tablet sekmesindeki Empty Activity |
| Application Name | ColorMyViews |
| Company Name android | com.android.example.colormyviews (veya kendi domain'iniz) |
| Language | Kotlin |
| Minimum API level | API 19: Android 4.4 (KitKat) |
| This project will support instant apps | (Bu kutuyu boş bırakın) |
| Use AndroidX artifacts | Bu kutuyu seçin. |

Empty Activity template'i, Mainactivity.kt dosyasında tek bir boş etkinlik oluşturur. Şablon ayrıca aktivite_main.xml adlı bir Layouts dosyası oluşturur. Layout, içerik olarak tek bir TextView ile kök görünüm grubu olarak ConstraintLayout'u kullanır.

2.Android Studio'nun Gradle derlemesini bitirmesini bekleyin. Herhangi bir hata görürseniz, **Build > Rebuild Project** yapın.

3.Uygulamayı çalıştırın ve derlemenin tamamlanması için birkaç saniye bekleyin. Tam ortada "Hello World!" yazan bir ekran görmelisiniz.

![image](https://user-images.githubusercontent.com/80598532/143635928-ea0cff87-7e74-44a9-afa3-25109a0c8d99.png)

## <a name="2"></a>ConstraintLayout Oluşturmak İçin Layout Editor Kullanın
Bu görevde, uygulamanız için bir ConstraintLayout oluşturmak için Android Studio Layout Editor'ünü kullanın.

### 1. Adım: Android Studio çalışma alanınızı kurun.

1. Activity_main.xml dosyasını açın ve Design sekmesine tıklayın.
2. Constraint'leri manuel olarak ekleyeceksiniz, bu yüzden otomatik bağlantının kapalı olmasını istiyorsunuz. Toolbar'da, aşağıda gösterilen Otomatik Bağlantıyı Aç/Kapat geçiş butonunu bulun. (Toolbar'ı göremiyorsanız, Layout Editor'ün Design Editor alanına tıklayın.) Otomatik bağlantının kapalı olduğundan emin olun.

![image](https://user-images.githubusercontent.com/80598532/143638469-fecdb907-caa4-4010-87d0-5bf088efd522.png) : Otomatik bağlantı açık.

![image](https://user-images.githubusercontent.com/80598532/143638845-79756e6b-086e-4951-b888-8a943960345d.png) : Otomatik bağlantı kapalı; bu codelab için istediğiniz şey budur.

3.Varsayılan kenar boşluklarını 16dp'ye ayarlamak için toolbar'ı kullanın. (Varsayılan 8dp'dir.)

![image](https://user-images.githubusercontent.com/80598532/143639861-2bcbf788-9b57-4f10-ad9e-72b53a9651da.png)

Varsayılan margini 16dp'ye ayarladığınızda, bu marginle yeni constraint'ler oluşturulur, böylece her constraint eklediğinizde margin eklemeniz gerekmez.

4. Hello World metni text view görünümünde görünene kadar araç çubuğunun sağ tarafındaki + simgesini ![image](https://user-images.githubusercontent.com/80598532/143642614-dabfb624-d028-475f-bb87-67f3ad36257c.png)
 kullanarak yakınlaştırın.
5. Attributes bölmesini açmak için Hello World metin görünümüne çift tıklayın.
![image](https://user-images.githubusercontent.com/80598532/143647123-5f64dd28-c955-44c3-aa62-636dc82b2523.png)

### Görünüm denetçisi
Aşağıdaki ekran görüntüsünde gösterilen görünüm denetçisi, Attributes bölmesinin bir parçasıdır. Görünüm denetçisi, constraintler, constraint türleri, constraint sapması ve marjinler gibi layout attributeleri için kontroller içerir.

![image](https://user-images.githubusercontent.com/80598532/143650016-634084dd-0e35-4f73-98f9-bb5cd5c8fb2b.png)

İpucu: Görünüm denetçisi yalnızca ConstraintLayout içindeki görünümler için kullanılabilir.

### Constraint Sapmaları
Constraint sapmaları, view'ı yatay ve dikey eksenler boyunca konumlandırır. Varsayılan olarak, görünüm %50 sapma ile iki kısıtlama arasında ortalanır.

Sapmayı ayarlamak için, görünüm denetçisinde sapma kaydırıcılarını (![image](https://user-images.githubusercontent.com/80598532/143656604-ec332a69-d2f2-4d77-984e-e909db85a124.png)
) sürükleyebilirsiniz. Bir sapma kaydırıcısını sürüklemek, görünümün eksen boyunca konumunu değiştirir.
Sapmayı ayarlamak için, görünüm denetçisinde sapma kaydırıcılarını ( sürükleyebilirsiniz. Bir sapma kaydırıcısını sürüklemek, görünümün eksen boyunca konumunu değiştirir.

### 2. Adım Hello World text view'ına marjin ekleyin
1.Görünüm denetçisinde, text görünümü için sol, sağ, üst ve alt kenar boşluklarının (marjinlerin) 0 olduğuna dikkat edin. Bu görünüm, siz default (varsayılan) kenar boşluğunu değiştirmeden önce oluşturulduğundan, varsayılan kenar boşluğu otomatik olarak eklenmedi.

2.Sol, sağ ve üst marjinler için görünüm denetçisindeki açılır menüden 16dp'yi seçin. Örneğin, aşağıdaki ekran görüntüsünde layout_marginEnd (layout_marginRight) ekliyorsunuz.

![image](https://user-images.githubusercontent.com/80598532/143656723-8806340f-3a9e-4015-ac2f-f904abf430dc.png)

### 3. Adım Text görünümü için marjin ce constraintleri ayarlayın
Görünüm denetçisinde, karenin içindeki oklar constraintlerin türünü temsil eder:

- ![image](https://user-images.githubusercontent.com/80598532/143658545-be884b86-c47d-44a5-995d-ec80866c70ac.png)
 Wrap Content : View, yalnızca içeriğini içermesi gerektiği kadar genişler.
 
- ![image](https://user-images.githubusercontent.com/80598532/143658561-f27b5ba0-4f09-476a-9a0c-d1136279eeff.png) Fixed : Sabit kısıtlamalı okların yanındaki metin kutusunda görünüm marjı olarak bir boyut belirtebilirsiniz.
- ![image](https://user-images.githubusercontent.com/80598532/143658567-641cd082-0603-48f0-9524-af78450751f8.png)  Constraints : GView, kendi marjlarını hesaba kattıktan sonra, her iki taraftaki constraintlerini karşılamak için mümkün olduğunca genişler. Bu kconstraint, yerleşimin farklı ekran boyutlarına ve yönelimlerine uyum sağlamasına izin verdiği için çok esnektir. Görünümün constraintlerle eşleşmesine izin vererek, oluşturduğunuz uygulama için daha az layout'a ihtiyacınız olur.

1.Görünüm denetçisinde, sol ve sağ constraintleri Match Constraints ![image](https://user-images.githubusercontent.com/80598532/143658678-920ddf01-8635-4ee4-903d-a566a31b6cf5.png)
 olarak değiştirin (Constraints türleri arasında geçiş yapmak için ok simgesini tıklayın.)

![image](https://user-images.githubusercontent.com/80598532/143658685-bbafde73-2f34-40ee-aedf-26c246616f1f.png)

2.Görünüm denetçisinde, alt constraint'i silmek için karenin üzerindeki Delete Bottom Constraint'e tıklayın.

3.Text sekmesine geçin. layout_marginStart için boyut kaynağını açın ve Resource name'i margin_wide olarak ayarlayın.

![image](https://user-images.githubusercontent.com/80598532/143658842-577551c4-3b51-45f4-a8f3-143e94ad6459.png)


4.Üst ve uç marjinler için aynı boyut kaynağını @dimen/margin_wide olarak ayarlayın.

```

android:layout_marginStart="@dimen/margin_wide"
android:layout_marginTop="@dimen/margin_wide"
android:layout_marginEnd="@dimen/margin_wide"

```
## <a name="3"></a>TextView'e Stil Verin

### 1. Adım: Font Ekleyin
1.Attributes bölmesinde, fontFamily'yi arayın ve yanındaki açılır oku seçin.  More Font'u seçin. Resources açılır.
![image](https://user-images.githubusercontent.com/80598532/143660632-6f56b016-0b45-47db-a8ea-6bc8f10f28d3.png)

2.Resouces'da Roboto'yu arayın
3.Roboto'ya tıklayın ve Preview listesinde Normal'i seçin.
4.Projeye yazı tipi ekle butonunu seçin.
5.Tamamı tıklayın.

![image](https://user-images.githubusercontent.com/80598532/145288748-c34e841a-15d2-428f-8a14-d27d93e20a97.png)

Bu, roboto.ttf yazı tipi dosyasını içeren bir res/font klasörü ekler.Ayrıca, text görünümünüz için @font/Roboto attribute'u ayarlanır.


### 2.Adım: Stil ekleyin

1.res/values/dimens.xml dosyasını açın ve yazı tipi boyutu için aşağıdaki boyut kaynağını ekleyin.

```

<dimen name="box_text_size">24sp</dimen>

```

2.res/values/styles.xml dosyasını açın ve text görünümü için kullanacağınız aşağıdaki stili ekleyin.

```
<style name="whiteBox">
   <item name="android:background">@android:color/holo_green_light</item>
   <item name="android:textAlignment">center</item>
   <item name="android:textSize">@dimen/box_text_size</item>
   <item name="android:textStyle">bold</item>
   <item name="android:textColor">@android:color/white</item>
   <item name="android:fontFamily">@font/roboto</item>
</style>
```
Bu stilde, arka plan rengi ve metin rengi varsayılan Android renk kaynaklarına ayarlanmıştır. Yazı tipi Roboto olarak ayarlanmıştır. Metin ortaya hizalanmış ve kalın yazılmıştır ve metin boyutu box_text_size olarak ayarlanmıştır.

### 3.Adım: Text View İçin Bir Dize Kaynağı Ekleyin

1. Nitelikler (Attributes) bölmesinde, text niteliğini bulun. (Anahtar simgesi olmayanı istiyorsunuz.)
2. Kaynaklar (Resources) iletişim kutusunu açmak için metin özelliğinin yanındaki ... (üç nokta) öğesini tıklayın.
3. Kaynaklar iletişim kutusunda Add new resource > New string Value seçin. Kaynak adını box_one ve değeri Box One olarak ayarlayın.
4. Tamam'ı tıklayın.

![image](https://user-images.githubusercontent.com/80598532/148471048-8b6f6bfb-c228-4a80-824b-903c56667902.png)

### 4.Adım: Text View için Attributes (Nitelik) Ayarlarını Bitirin

1. Attributes bölmesinde, text view'un id'sini box_one_text olarak ayarlayın.
2. Stili @style/whiteBox olarak ayarlayın.
3. Kodu temizlemek için text sekmesine geçin ve android:fontFamily="@font/roboto" attribute'unu kaldırın, çünkü bu yazı tipi whiteBox stilinde mevcuttur.
4. Design sekmesine geri dönün. Design editor'ün üst kısmında, Önizleme için Cihaz (D) düğmesini tıklayın. Farklı ekran konfigürasyonlarına sahip cihaz türlerinin bir listesi görüntülenir. Varsayılan cihaz Pixel'dir.

![image](https://user-images.githubusercontent.com/80598532/148471285-1eefbdf3-aad5-4625-a779-940a1fa91b17.png)

5. Listeden farklı cihazlar seçin ve TextView'in farklı ekran konfigürasyonlarına nasıl uyum sağladığını görün.
6. Uygulamanızı çalıştırın. "Box One" metniyle birlikte stil sahibi bir yeşil text view görürsünüz.

![image](https://user-images.githubusercontent.com/80598532/148471386-16a572cd-89ba-4ae4-a928-d67b85b29595.png)



## <a name="4"></a>İkinci bir TextView Ekleyin ve Constrait Ekleyin (kısıtlamalar)

Bu görevde, box_one_text'in altına başka bir text view eklersiniz. Yeni text view box_one_text ve layoutun üst öğesiyle sınırlandırırsınız. 

### 1. Adım: Yeni bir metin görünümü ekleyin

1. Activity_main.xml dosyasını açın ve Design sekmesine geçin.
2. Palet bölmesinden bir TextView'i aşağıda gösterildiği gibi doğrudan designn editor önizlemesine sürükleyin. Text view'u sol kenar boşluğuyla hizalı olarak box_one_text'in altına yerleştirin.

![image](https://user-images.githubusercontent.com/80598532/145289842-f0604e61-d877-4899-894e-509dd01b38ed.png)


3. Design editor'de, yeni text view'a tıklayın, ardından işaretçiyi text view'un üst tarafındaki noktanın üzerinde tutun. Aşağıda gösterilen bu noktaya kısıtlama tutamacı (constraint handle) denir.

![image](https://user-images.githubusercontent.com/80598532/148469320-c282cb95-e75a-4f0d-ac67-fc81d03f0794.png)

İşaretçiyi kısıtlama tutamacı (constraint handle) üzerinde tuttuğunuzda tutamaç (handle) yeşile döner ve yanıp söner.

### 2. Adım: Yeni Text View'e Constraints Ekleyin

Yeni text view'ün üst kısmını "Box One" metin görünümünün altına bağlayan bir constraint oluşturun:

1. Yeni text view'de işaretçiyi üst constraint handle üzerinde tutun.
2. Görünümün üst constraint handle'ına tıklayın ve yukarı sürükleyin. Bir kısıtlama çizgisi belirir. Kısıtlama çizgisini aşağıda gösterildiği gibi Box One text view'unun altına bağlayın.

![image](https://user-images.githubusercontent.com/80598532/148470110-9fa6f3e9-ed31-415c-9093-915651e8c9fa.png)


Tıklamayı bıraktığınızda, kısıtlama oluşturulur ve yeni text view Box One'ın altındaki 16 dp'lik mesafeye taşınır. (Yeni text view'un üst kenar boşluğu 16 dp'dir çünkü bu, daha önce belirlediğiniz varsayılan değerdir.)


Şimdi bir sol kconstraint oluşturun:
1. Yeni view'ın sol tarafındaki constraint handle'a tıklayın.
2. Kısıtlama çizgisini layout'un (sayfanın) sol kenarına sürükleyin.

![image](https://user-images.githubusercontent.com/80598532/148470305-4fe0e5a8-588a-4145-9b8e-b2996f3de101.png)


İpucu: Görünüm denetçisini kullanarak da constraintler oluşturabilirsiniz. Örneğin, yeni text view'da bir sol constraint oluşturmak için:

1. Önizlemede,yeni tex view'a seçmek için tıklayın.
2. Görünüm denetçisinde, aşağıda gösterildiği gibi kutunun sol tarafındaki + simgesini ![image](https://user-images.githubusercontent.com/80598532/148470468-c06ed04a-c40d-4571-aa5b-2fc2d2203199.png) tıklayın.

![image](https://user-images.githubusercontent.com/80598532/148470486-b2c03ef3-7687-452a-bccd-d9256191f8b3.png)

Bu şekilde bir constraint oluşturduğunuzda, constraint üst öğeye veya ona daha yakın bir görünüme eklenir.


### 3.Adım: Yeni Text View için Attributes (Nitelikleri) Ayarlayın

1. res/values/strings.xml dosyasını açın. Aşağıdaki kodla yeni bir dize kaynağı ekleyin:

```
<string name="box_two">Box Two</string>

```

2. Activity_main.xml dosyasını açın ve Design sekmesine tıklayın. Yeni text view'da aşağıdaki attribute'ları (nitelikleri) ayarlamak için Attributes bölmesini kullanın:

| Attribute | Value |
|---|---|
| id | box_two_text |
| layout_height | 130dp |
| layout_width | 130dp |
| style | @style/whiteBox |
| text | @string/box_two |


Bu durumda, text view'un yüksekliği ve genişliği için sabit boyutlar atarsınız. Yalnızca görünümünüzün tüm cihazlarda ve düzenlerde her zaman sabit bir boyutu olması gerekiyorsa, yükseklik ve genişlik için sabit boyutlar atayın.


Önemli: Gerçek dünya uygulamaları geliştirirken, mümkün olduğunda UI öğelerinizin yüksekliği ve genişliği için esnek kısıtlamalar kullanın. Örneğin, match_constraint veya wrap_content kullanın. Uygulamanızda ne kadar sabit boyutlu UI öğelerine sahipseniz, düzeniniz farklı ekran yapılandırmaları için o kadar az uyarlanabilir.

3. Uygulamanızı çalıştırın. Aşağıdaki ekran görüntüsüne benzer şekilde, biri diğerinin üzerinde iki yeşil TextView görünümü görmelisiniz:

![image](https://user-images.githubusercontent.com/80598532/148472291-7f6e3813-40a4-40b1-b346-a91a3819ab4d.png)



## <a name="5"></a>TextView Görünümleri Zinciri Oluşturun

Bu görevde, üç TextView görünümü eklersiniz. Text view'lar birbirleriyle dikey olarak ve "Box Two"ntext view'u ile yatay olarak hizalanır. Görünüşler bir zincir halinde olacak.

### Zincirler
Zincir, çift yönlü constraintslerle birbirine bağlanan bir görüş grubudur. Bir zincir içindeki görünümler, dikey veya yatay olarak dağıtılabilir. Örneğin, aşağıdaki diyagram, yatay bir zincir oluşturan, birbiriyle sınırlı iki görünümü göstermektedir.

![image](https://user-images.githubusercontent.com/80598532/148472831-13bdc618-5098-4b0d-b455-59d6b6245703.png)

#### Zincirin Başı
Bir zincirdeki ilk görünüme zincirin başı denir. Zincirin başına ayarlanan öznitelikler, zincirdeki tüm görünümleri kontrol eder, konumlandırır ve dağıtır. Yatay zincirler için baş en soldaki görünümdür. Dikey zincirler için baş, en üstteki görünümdür. Aşağıdaki iki diyagramın her birinde "A" zincirin başıdır.


![image](https://user-images.githubusercontent.com/80598532/148472958-301c2626-70d2-40cd-af75-8f5056619433.png)

#### Zincir Stilleri

Zincir stilleri, zincirleme görünümlerin yayılma ve hizalanma şeklini tanımlar. Bir zincir stili özniteliği atayarak, ağırlık ekleyerek veya görünümlere önyargı ayarlayarak bir zincire stil verirsiniz.

Üç zincir stili vardır:
 - Spread (Yayılma): Bu, default (varsayılan) stildir. Kenar boşlukları hesaba katıldıktan sonra görünümler kullanılabilir alana eşit olarak yayılır.

![image](https://user-images.githubusercontent.com/80598532/148473156-554903b3-7698-4f99-9803-d6805ab5adde.png)

- Spread Inside (İçeriye yayılma): İlk ve son görünümler, zincirin her iki ucundaki üst öğeye eklenir. Görünümlerin geri kalanı, kullanılabilir alana eşit olarak yayılır.

![image](https://user-images.githubusercontent.com/80598532/148473256-9fe5f88a-943f-48d4-a1c6-0497a2f04907.png)

- Packed (Paketlenmiş): Kenar boşlukları hesaplandıktan sonra görünümler birlikte paketlenir. Ardından, zincirin baş görünümünün sapmasını değiştirerek tüm zincirin konumunu ayarlayabilirsiniz.

![image](https://user-images.githubusercontent.com/80598532/148473395-68a45a15-f9f1-41a9-af9d-787def921e3b.png)

- Weighted (Ağırlıklı): Görünümler, layout_constraintHorizontal_weight veya layout_constraintVertical_weight özniteliklerinde ayarlanan değerlere göre tüm alanı dolduracak şekilde yeniden boyutlandırılır. Örneğin, A, B ve C olmak üzere üç görünüm içeren bir zincir hayal edin. A Görünümü 1 ağırlık kullanır. B ve C görünümlerinin her biri 2 ağırlık kullanır. B ve C görünümlerinin kapladığı alan A görünümünün iki katıdır. , Aşağıda gösterildiği gibi.


![image](https://user-images.githubusercontent.com/80598532/148473576-f4874d0f-a96e-4561-9f9b-5cb3674a0f2c.png)

Bir zincire zincir stili eklemek için, zincirin başı için layout_constraintHorizontal_chainStyle veya layout_constraintVertical_chainStyle attribute'unu ayarlayın. Bu görevde öğrendiğiniz Layout Editor'de zincir stilleri ekleyebilirsiniz.

Alternatif olarak, XML koduna zincir stilleri ekleyebilirsiniz. Örneğin:


```
// Horizontal spread chain
app:layout_constraintHorizontal_chainStyle="spread"

// Vertical spread inside chain
app:layout_constraintVertical_chainStyle="spread_inside"

// Horizontal packed chain
app:layout_constraintHorizontal_chainStyle="packed"

```
### 1.Adım: Üç Text View Ekleyin ve Dikey Bir Zincir Oluşturun

1. Design sekmesinde aktivite_main.xml dosyasını açın. Palet bölmesinden üç TextView görünümünü tasarım düzenleyicisine (design editor) sürükleyin. Üç yeni text view'u de aşağıda gösterildiği gibi "Box Two" text view'unun sağına koyun.

![image](https://user-images.githubusercontent.com/80598532/148473948-cc306e89-81c4-4795-967e-85045dc76254.png)

2. strings.xml dosyasında, yeni text view'lerin adları için aşağıdaki dize kaynaklarını ekleyin:


```
<string name="box_three">Box Three</string>
<string name="box_four">Box Four</string>
<string name="box_five">Box Five</string>

```

3. Yeni text view'lar için aşağıdaki attributeleri ayarlayın:

| Attribute | Top Text View | Middle Text View | Bottom Text View |
|---|---|---|---|
| id | box_three_text | box_four_text | box_five_text |
| text | @string/box_three | @string/box_four | @string/box_five |
| style | @style/whiteBox | @style/whiteBox | @style/whiteBox |

![image](https://user-images.githubusercontent.com/80598532/148474329-d5d49303-22d7-41dc-af97-78884c512d36.png)

Component Tree'de (bileşen ağacında), Bileşen Ağacında, eksik attributelarla ilgili hatalar görürsünüz. Bu hataları daha sonra düzeltirsiniz.

### 2.Adım: Bir Zincir Oluşturun ve Onu "Box Two" Yüksekliğiyle Sınırlayın

1. Üç yeni text view'un tümünü seçin, sağ tıklayın ve Chains > Create Vertical Chain'i seçin.

![image](https://user-images.githubusercontent.com/80598532/148474500-721e9b0c-7d3b-432e-b34e-541bc2d0ffc9.png)

Bu, "Box One"'dan layoutun sonuna kadar uzanan dikey bir zincir oluşturur.

2. éBox Three" tepesinden "Box Two" üstüne uzanan bir constraint ekleyin. Bu, mevcut üst constraint'i kaldırır ve onu yeni constraint ile değiştirir. Constraint'i açıkça silmeniz gerekmez.

![image](https://user-images.githubusercontent.com/80598532/148474666-7a390c26-6f0b-4a89-8616-b07fa14d0d54.png)

3. "Box Five" altından "Box Two" altına bir constraint ekleyin.

![image](https://user-images.githubusercontent.com/80598532/148474732-b028bb2c-03cc-4078-8285-a0146a4bbf2f.png)

Üç text view'un artık "Box Two"'nun üst ve alt kısımlarıyla sınırlandırıldığını gözlemleyin.


### 3.Adım: Sağa ve Sola Constraints Ekleyin

1. "Box Three"'nin sol tarafını "Box Two"'nun sağ tarafıyla sınırlayın. "Box Four" ve "Box Five" için tekrarlayın, her birinin sol tarafını "Box Two"'nun sağ tarafıyla sınırlayın.

![image](https://user-images.githubusercontent.com/80598532/148474887-4f0f35a6-a4cb-42ed-978b-0f5f279b44cb.png)

2. Üç text view'un her birinin sağ tarafını layoutun sağ tarafıyla sınırlayın.

![image](https://user-images.githubusercontent.com/80598532/148474950-f4cac0ee-6679-486c-a258-07234453cd71.png)

3. Üç text view'un her biri için, constraint türünü "Match Constraints" olarak değiştirmeye eşdeğer olan layout_width niteliğini 0dp'ye değiştirin.

![image](https://user-images.githubusercontent.com/80598532/148475224-e10a1f87-1d59-4b28-8a29-2552d86d4ca4.png)

### 4.Adım: Margin Ekleme

Aralarına boşluk eklemek için üç text view'da Layout_margin niteliklerini ayarlamak için Attributes bölmesini kullanın.

1. "Box Three" için, başlangıç ve bitiş kenar boşlukları için @dimen/margin_wide kullanın. Diğer kenar boşluklarını kaldırın.
2. "Box Four" için başlangıç, bitiş, üst ve alt kenar boşlukları için @dimen/margin_wide kullanın. Diğer kenar boşluklarını kaldırın.
3. "Box Five" için, başlangıç ve bitiş kenar boşlukları için @dimen/margin_wide kullanın. Diğer kenar boşluklarını kaldırın.
4. Uygulamanızdaki text viewların cihaz yapılandırma değişikliklerine nasıl uyum sağladığını görmek için önizlemenin yönünü değiştirin. Bunu yapmak için, araç çubuğunda Önizleme için Oryantasyon (O) simgesine ![image](https://user-images.githubusercontent.com/80598532/148475499-01ce08b1-fed4-4eb5-81bf-baea1772aa74.png) tıklayın ve Landscape'ı seçin.

![image](https://user-images.githubusercontent.com/80598532/148475520-51857e74-a4ab-4ade-9a49-0e14a75b8c4b.png)

5. Uygulamayı çalıştırın. Beş tarz TextView görünümü görmelisiniz. Constraint'lerin daha geniş bir ekranda nasıl davrandığını görmek için uygulamayı Nexus 10 gibi daha büyük bir cihazda veya öykünücüde çalıştırmayı deneyin.

![image](https://user-images.githubusercontent.com/80598532/148475610-2abcf73e-3c26-43cc-a76f-3908fcda1958.png)



## <a name="6"></a>Text görünümerine ClickHandler Ekleyin

Bu görevde ColorMyViews uygulamasını biraz daha renkli hale getiriyorsunuz. İlk önce tüm metin görünümlerinin rengini beyaz olarak değiştirirsiniz. Ardından, kullanıcı dokunduğunda görünümün rengini ve layout arka plan rengini değiştiren bir click handler eklersiniz.

1. style.xml dosyasında, whiteBox stilinin içinde arka plan rengini beyaz olarak değiştirin. Metin görünümleri beyaz yazı tipiyle beyazdan başlayacak ve ardından kullanıcı bunlara dokunduğunda renkleri değiştirecektir.

```
<item name="android:background">@android:color/white</item>

```

2. MainActivity.kt'de onCreate() işlevinden sonra makeColored() adlı bir işlev ekleyin. İşlevin parametresi olarak View'u kullanın. Bu görünüm, rengi değişecek olandır.

```
private fun makeColored(view: View) {
}

```

Her görünümün bir resource id'si vardır. Resource id (Kaynak kimliği), layout dosyasındaki aktivite_main.xml görünümün kimliği özelliğine atanan değerdir. Bir renk ayarlamak için kod, view'un Resource id'sinde bir When ifadesi kullanarak değişecektir. Tıklama eylemi aynı olduğunda birçok view için bir tıklama işleyici işlevini kullanmak yaygın bir kalıptır.

3. makeColored() işlevini uygulayın: view'un Resource id'sini kontrol etmek için bir when bloğu ekleyin. Color sınıfı sabitlerini kullanarak view'un arka plan rengini değiştirmek için her view id'sinde setBackgroundColor() işlevini çağırın. Kod girintisini düzeltmek için Code > Reformat code'u seçin.

```
private fun makeColored(view: View) {
   when (view.id) {
      
       // Boxes using Color class colors for the background
       R.id.box_one_text -> view.setBackgroundColor(Color.DKGRAY)
       R.id.box_two_text -> view.setBackgroundColor(Color.GRAY)
       R.id.box_three_text -> view.setBackgroundColor(Color.BLUE)
       R.id.box_four_text -> view.setBackgroundColor(Color.MAGENTA)
       R.id.box_five_text -> view.setBackgroundColor(Color.BLUE) 
   }
}

```

4. Çalıştırmak için az önce eklediğiniz kodun android.graphics.Color kitaplığına ihtiyacı var. Android Studio bu kitaplığı otomatik olarak içe aktarmadıysa, kitaplığı MainActivity sınıf tanımından önce eklemek için bir import ifadesi kullanın.
5. Kullanıcı arka plana dokunursa, arka plan renginin açık griye dönüşmesini istersiniz. Açık renkli bir arka plan, görünümlerin ana hatlarını ortaya çıkaracak ve kullanıcıya bir sonraki nereye dokunulacağı konusunda bir ipucu verecektir.

Id, viewların hiçbiriyle eşleşmiyorsa, kullanıcının arka plana dokunduğunu bilirsiniz. When ifadesinin sonuna başka bir ifade ekleyin. Diğerinin içinde, arka plan rengini açık gri olarak ayarlayın.

```
else -> view.setBackgroundColor(Color.LTGRAY)

```

6. Activity_main.xml dosyasında, ConstraintLayout root (kök) dizinine bir id ekleyin. Android sisteminin rengini değiştirmek için bir tanımlayıcıya ihtiyacı vardır.

```
android:id="@+id/constraint_layout"

```

7. MainActivity.kt'de, her view'de tıklama-dinleyici işlevi makeColored()'ı ayarlamak için setListeners() adlı bir işlev ekleyin. Her text view ve root layout için bir referans almak için findViewByID kullanın. Her referansı bir değişkene atayın.

```
private fun setListeners() {

   val boxOneText = findViewById<TextView>(R.id.box_one_text)
   val boxTwoText = findViewById<TextView>(R.id.box_two_text)
   val boxThreeText = findViewById<TextView>(R.id.box_three_text)
   val boxFourText = findViewById<TextView>(R.id.box_four_text)
   val boxFiveText = findViewById<TextView>(R.id.box_five_text)

   val rootConstraintLayout = findViewById<View>(R.id.constraint_layout)
}

```

Bu kodun çalışması için android.widget.TextView kitaplığına ihtiyacı var. Android Studio bu kitaplığı otomatik olarak içe aktarmazsa, kitaplığı MainActivity sınıf tanımından önce eklemek için bir import ifadesi kullanın.

8. setListeners() fonksiyonunun sonunda, bir view listesi tanımlayın. Listeye tıklanabilir Viewlar adını verin ve tüm view örneklerini listeye ekleyin.


```
fun setListeners() {
...
   val clickableViews: List<View> =
       listOf(boxOneText, boxTwoText, boxThreeText,
              boxFourText, boxFiveText, rootConstraintLayout)
  }

```

9.setListeners() işlevinin sonunda, her view için lintener (dinleyiciyi) ayarlayın. Bir for döngüsü ve setOnClickListener() işlevini kullanın. 

```
   for (item in clickableViews) {
       item.setOnClickListener { makeColored(it) }

```

10. MainActivity.kt'de onCreate() fonksiyonunun sonunda setListeners()'a bir çağrı yapın.


```
override fun onCreate(savedInstanceState: Bundle?) {
...
   setListeners()
}

```

11. Uygulamanızı çalıştırın. İlk başta boş bir ekran görüyorsunuz. Viewları ve arka planı ortaya çıkarmak için ekrana dokunun. Devam edin ve kendi başınıza daha fazla view ve renkle daha fazlasını deneyin.

![image](https://user-images.githubusercontent.com/80598532/148479299-3b6de5da-68a8-4ee8-8307-9e9da744e18b.png)


## <a name="7"></a>Temel Constrait Ekleyin

### Temel Constraint




## <a name="8"></a>Buton Zinciri Ekleyin

Bu görevde, üç Buton görünümü ekler ve bunları birbirine zincirlersiniz.

### 1.Adım: Üç Buton Ekleyin

1. Design sekmesinde aktivite_main.xml dosyasını açın. Palet bölmesinden üç butonu layoutun altına sürükleyin.

![image](https://user-images.githubusercontent.com/80598532/148479657-52d60b91-051e-43ee-8321-5ec954c33efb.png)

2. strings.xml dosyasında, Buton görünümleri için aşağıdaki dize kaynaklarını ekleyin:

```
<string name="button_red">RED</string>
<string name="button_yellow">YELLOW</string>
<string name="button_green">GREEN</string>

```

3. Buton görünümlerine aşağıdaki attribute'leri ayarlayın:

| Attribute | Left Button | Middle Button | Right Button |
|---|---|---|---|
| id | red_button | yellow_button | green_button |
| text | @string/button_red | @string/button_yellow | @string/button_green |

Buton etiketlerini birbiriyle dikey olarak hizalayın. Bunu yapmak için red_button ve green_button taban çizgilerini yellow_button'ın taban çizgisiyle sınırlayın. (Bir view'e taban çizgisi constrainti eklemek için, view'a tıklayın ve view'un altında görünen Edit Baseline simgesini ![image](https://user-images.githubusercontent.com/80598532/148480037-90f01c86-08e4-42a1-bb33-3e23fae7d577.png) kullanın.)

![image](https://user-images.githubusercontent.com/80598532/148480050-2bf87ce8-1007-4709-a02d-471992e4ffa4.png)

İpucu: Taban çizgisi constrainti ve alt constraint birbirini dışlar, bu nedenle aynı view (görünüm) için ikisini birden oluşturamazsınız. Bir alt constraint ve ardından bir taban çizgisi constrainti eklerseniz, Layout Editor alt kısıtlamayı kaldırır.

### 2.Adım: Yatay (Horizontal) Bir Zincir Oluşturun ve Onu Sınırlayın (Constraint Uygulayın)

1. Design Editor'de veya Component Tree'de üç buton görünümünün tümünü seçin ve sağ tıklayın. Chains > Create Horizontal chain seçin.

![image](https://user-images.githubusercontent.com/80598532/148480345-678148c1-c675-4540-a1e4-b6721fdd75df.png)

2. Bu marginler henüz ayarlanmamışsa, yellow_button için 16dp'lik sağ ve sol marginleri ayarlamak için görünüm denetçisini kullanın.

![image](https://user-images.githubusercontent.com/80598532/148480426-a4135b0f-439b-4fea-9d2c-4d2928f36b02.png)

3. Görünüm denetçisini kullanarak red_button'ın sol marginini 16dp'ye ayarlayın. green_button'ın sağ marginini 16dp'ye ayarlayın.
4. Sarı_düğmenin üst kısmını info_text'in alt kısmıyla bağlayın (sınırlayın).
5. Yellow_button'ın altını düzenin alt kısmıyla sınırlayın (bağlayın).

![image](https://user-images.githubusercontent.com/80598532/148480568-1d8a0f1d-6977-4082-bb34-55cb3d799f7f.png)

6. Butonları layoutun altına bırakmak için yellow_button'ın dikey sapmasını 100 (XML'de 1.0) olarak değiştirin.
7. Layoutunuzu farklı cihazlar ve yönler için test edin. Layout, tüm aygıtlar ve yönler için çalışmayabilir, ancak çoğu için çalışmalıdır.

Buton görünümleri için oluşturulan XML kodu aşağıdakine benzer olacaktır:


```

<Button
   android:id="@+id/red_button"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   android:layout_marginStart="@dimen/margin_wide"
   android:text="@string/button_red"
   android:visibility="visible"
   app:layout_constraintBaseline_toBaselineOf="@+id/yellow_button"
   app:layout_constraintEnd_toStartOf="@+id/yellow_button"
   app:layout_constraintHorizontal_bias="0.5"
   app:layout_constraintStart_toStartOf="parent" />

<Button
   android:id="@+id/yellow_button"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   android:layout_marginStart="@dimen/margin_wide"
   android:layout_marginTop="@dimen/margin_wide"
   android:layout_marginBottom="@dimen/margin_wide"
   android:text="@string/button_yellow"
   android:visibility="visible"
   app:layout_constraintBottom_toBottomOf="parent"
   app:layout_constraintEnd_toStartOf="@+id/green_button"
   app:layout_constraintHorizontal_bias="0.5"
   app:layout_constraintStart_toEndOf="@+id/red_button"
   app:layout_constraintTop_toBottomOf="@+id/info_text"
   app:layout_constraintVertical_bias="1.0" />

<Button
   android:id="@+id/green_button"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   android:layout_marginEnd="16dp"
   android:text="@string/button_green"
   app:layout_constraintBaseline_toBaselineOf="@+id/yellow_button"
   app:layout_constraintEnd_toEndOf="parent"
   app:layout_constraintHorizontal_bias="0.5"
   app:layout_constraintStart_toEndOf="@+id/yellow_button" />
   
```


## <a name="9"></a>Butonlara ClickHandlers Ekleyin

Bu görevde, her bir Buton görünümüne bir tıklama işleyicisi (ClickHandlers) eklersiniz. Tıklama işleyici (ClickHandlers), TextView görünümlerinin rengini değiştirir.

1. res/values/colors.xml dosyasına aşağıdaki renkleri ekleyin:

```
<color name="my_green">#12C700</color>
<color name="my_red">#E54304</color>
<color name="my_yellow">#FFC107</color>

```

2. MainActivity.kt'de, buton görünümleri için referanslar almak için findViewById'i kullanın. Bunu yapmak için, aşağıdaki kodu setListeners() tıklama işleyici işlevinin içine, clickableViews bildiriminin üzerine koyun:


```

val redButton = findViewById<Button>(R.id.red_button)
val greenButton = findViewById<Button>(R.id.green_button)
val yellowButton = findViewById<Button>(R.id.yellow_button)

```


3. setListeners() içinde,Buton görünümlerinin referanslarını tıklanabilir viewlar listesine ekleyin.


```

private fun setListeners() {
   ...
   val clickableViews: List<View> =
       listOf(boxOneText, boxTwoText, boxThreeText,
boxFourText, boxFiveText, rootConstraintLayout,
redButton, greenButton, yellowButton
)
   ... 
}

```



4. makeColored() işlevinin içine, kullanıcı butonlara dokunduğunda text viewlarının renklerini değiştirmek için kod ekleyin. Bir view arka planı olarak kaynaklarda tanımlanan özel bir renk ayarlamak için setBackgroundResource() işlevini kullanın. View'un arka planı olarak önceden tanımlanmış renkleri ayarlamak için setBackgroundColor() işlevini kullanın. Yeni kodu, gösterildiği gibi, else ifadesinin üzerine ekleyin:


```

private fun makeColored(view: View) {
   when (view.id) {

      ...

       // Boxes using custom colors for background
       R.id.red_button -> box_three_text.setBackgroundResource(R.color.my_red)
       R.id.yellow_button -> box_four_text.setBackgroundResource(R.color.my_yellow)
       R.id.green_button -> box_five_text.setBackgroundResource(R.color.my_green)

       else -> view.setBackgroundColor(Color.LTGRAY)
   }
}

```

5. Son uygulamanızı çalıştırın. Text view'larına ve butonlara tıklayın. Ekranınız böyle bir şeye benzeyecek.


![image](https://user-images.githubusercontent.com/80598532/148481170-e51b22f7-8597-4f77-92ed-22374476c68d.png)







