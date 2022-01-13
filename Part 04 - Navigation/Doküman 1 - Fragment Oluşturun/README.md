# Fragment Oluşturun

- [Genel Bakış](#0) 
- [Başlangıç Uygulaması Projesini Keşfedin](#1) 
- [Fragment Ekleyin](#2) 

## <a name="0"></a>Aşama Ø : Genel Bakış

Bu dersi oluşturan üç kod laboratuvarında AndroidTrivia adlı bir uygulama üzerinde çalışıyorsunuz. Tamamlanan uygulama, kullanıcının Android kodlaması hakkında üç önemsiz soruyu yanıtladığı bir oyundur. Kullanıcı üç soruyu da doğru cevaplarsa oyunu kazanır ve sonuçlarını paylaşabilir.

![Ekran Resmi 2021-12-12 03 35 03](https://user-images.githubusercontent.com/70329389/145696137-595bb2dd-d947-4b19-bde8-012748e93c63.png)

AndroidTrivia uygulaması, navigation kalıplarını ve kontrollerini gösterir. Uygulamanın birkaç bileşeni vardır:

- Yukarıdaki ekran görüntüsünde solda gösterilen başlık ekranında, kullanıcı oyunu başlatır.
- Yukarıda ortada gösterilen soruların olduğu oyun ekranında, kullanıcı oyunu oynar ve cevaplarını gönderir.
- Sağ üstte gösterilen navigation drawer, uygulamanın yanından dışarı doğru kayar ve başlık içeren bir menü içerir. drawer simgesi navigation drawer'ı açar. Navigation drawer menüsü, Hakkında sayfasına bir bağlantı ve oyunun kurallarına bir bağlantı içerir.

Uygulamanın üst kısmında, uygulamanın adını gösteren *uygulama çubuğu (*veya işlem çubuğu) adı verilen bir görünüm görüntülenir .

## <a name="1"></a>Aşama 1 : Başlangıç Uygulaması Projesini Keşfedin

Bu kod laboratuvarında, Trivia uygulamasını tamamlarken ihtiyaç duyduğunuz şablon kodu ve Parça sınıflarını sağlayan bir başlangıç uygulamasından çalışırsınız.

1. AndroidTrivia-Starter Android Studio projesini indirin. Android-kotlin-fundamentals-starter-apps zip dosyasının tamamını indirmeniz gerekebilir.
2. Projeyi Android Studio'da açın ve uygulamayı çalıştırın. Uygulama açıldığında, uygulama adını ve boş bir ekranı görüntülemekten başka bir şey yapmıyor.

 ![image](https://user-images.githubusercontent.com/80598532/149355693-9e5fea2d-2606-4998-bc61-81e7fbc9508f.png)
 
3. Android Studio Proje bölmesinde, proje dosyalarını keşfetmek için Proje: Android görünümünü açın. MainActivity sınıfını ve Fragment sınıflarını görmek için app > Java klasörünü açın.
4. 
![image](https://user-images.githubusercontent.com/80598532/149355964-a45e3545-23a0-4931-9181-1b8de1177995.png)

4. res > layout klasörünü açın ve Activity_main.xml'e çift tıklayın. Activity_main.xml dosyası, Layout Editor'de görünür.
5. Design sekmesini açın. Activity_main.xml dosyası için Component Tree, root'u dikey(vertical) LinearLayout olarak gösterir.

![image](https://user-images.githubusercontent.com/80598532/149356438-a9125758-cc9f-4260-ba21-28733974186c.png)

- Vertical Linear Layout'ta, düzendeki tüm alt görünümler dikey olarak hizalanır.

## <a name="2"></a>Aşama 2 : Fragment Ekleyin.

Fragment, bir Activity'deki bir davranışı veya kullanıcı arayüzünün(UI) bir bölümünü temsil eder. Çok bölmeli bir kullanıcı arayüzü oluşturmak için birden çok Fragment'ı tek bir Activity'de birleştirebilir ve bir Fragment'ı birden çok Activity'de yeniden kullanabilirsiniz.

Fragment'ı, diğer Activity'lerde de kullanabileceğiniz bir "sub-activity" gibi, bir Activity'nin modüler bir bölümü olarak düşünün:

- Bir Fragment'ın kendi yaşam döngüsü (lifecycle'ı) vardır ve kendi girdi olaylarını alır.
- Activity çalışırken Fragment ekleyebilir veya kaldırabilirsiniz.
- Bir Kotlin sınıfında bir Fragment tanımlanır.
- Bir Fragment'ın aullanıcı arayüzü, bir XML Layout dosyasında tanımlanır.

AndroidTrivia uygulamasının bir "main activity" ve birkaç Fragment'ı vardır. Fragmentların çoğu ve bunların Layout dosyaları sizin için tanımlanmıştır. Bu görevde, bir fragment oluşturur ve bu fragment'ı uygulamanın main activity'sine eklersiniz.

### 1.Adım: Bir Fragment Class'ı Ekleyin.
Bu adımda boş bir TitleFragment classı oluşturacaksınız. Yeni bir Fragment için bir Kotlin classı oluşturarak başlayın:

1. Android Studio'da, odağı proje dosyalarına geri getirmek için Proje bölmesinin içinde herhangi bir yeri tıklayın. Örneğin, com.example.android.navigation klasörüne tıklayın.
2. File > New > Fragment > Fragment (Blank) seçin.
3. Fragment adı için TitleFragment girin.
4. Fragment layout adı için placeholder_layout girin (bu layout'u, TitleFragment için tasarlanmış layout'u zaten olduğundan uygulamamız için kullanmayacağız).
5. Resource language için Kotlin'i seçin.
6. Bitir'i tıklayın.
7. Eğer açık değilse, TitleFragment.kt fragment dosyasını açın. Bir Fragment'in yaşam döngüsü sırasında çağrılan yöntemlerden biri olan onCreateView() metodunu içerir.
8. onCreateView() içindeki kodu silin. onCreateView() işlevi yalnızca aşağıdaki kodla bırakılır:

```
override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                         savedInstanceState: Bundle?): View? {
}
```

9. TitleFragment classında onCreate() yöntemini, fragment initialize(başlatma) parametrelerini ve companion object'i (tamamlayıcı nesneyi) silin. TitleFragment class'ınızın aşağıdaki gibi göründüğünden emin olun:

```
class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        
    }
}
```

#### Binding Object (Bağlayıcı Nesne) oluşturun.
Fragment şimdi derlenmeyecek. Fragment'i derlemek için, bir binding object (bağlayıcı nesne) oluşturmanız ve Fragment'in görünümünü inflate etmeniz gerekir (bir Aktivite için setContentView() kullanmaya eşdeğerdir).

1. TitleFragment.kt'deki onCreateView() yönteminde, bir binding(bağlama) değişkeni (val binding) oluşturun.
2. Fragment'in görünümünü inflate etmek için, Fragment'in Binding nesnesindeki FragmentTitleBinding olan DataBindingUtil.inflate() yöntemini çağırın.

DataBindingUtil.inflate yöntemine dört bağımsız değişken iletin:
- Binding layout'u inflate etmek için kullanılan LayoutInflater olan inflater.
- Inflate edilecek layoutun XML layout kaynağı. Sizin için önceden tanımlanmış olan layoutlardan birini kullanın, R.layout.fragment_title.
- Ana ViewGroup için kapsayıcı. (Bu parametre isteğe bağlıdır.)
- AttachToParent değeri için false.
- DataBindingUtil.inflate tarafından döndürülen binding'i binding variable değişkenine atayın.
- Inflate view'dan binding.root'u döndürün. onCreateView() yönteminiz şimdi aşağıdaki koda benziyor:

```
override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                         savedInstanceState: Bundle?): View? {
   val binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater,
           R.layout.fragment_title,container,false)
   return binding.root
   }
```

5. res>layout'u açın ve placeholder_layout.xml'i silin.

### 2.Adım: Main Layout Dosyasına Yeni Fragment Ekleyin.
Bu adımda, uygulamanın Activity_main.xml layout dosyasına TitleFragment'i eklersiniz.

1. res > layout > aktivite_main.xml dosyasını açın ve layout XML kodunu görüntülemek için Kod sekmesini seçin.
2. Mevcut LinearLayout öğesinin içine bir fragment öğesi ekleyin.
3. Fragment'ın id'sini titleFragment olarak ayarlayın.
4. Fragment'ın adını, burada com.example.android.navigation.TitleFragment olarak tüm Fragment class'ına uygulayon.
5. Layout genişliğini ve yüksekliğini match_parent olarak ayarlayın.

```
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">
            <fragment
                android:id="@+id/titleFragment"
                android:name="com.example.android.navigation.TitleFragment"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                />
        </LinearLayout>

</layout>
```

6. Uygulamayı çalıştırın. Fragment ana ekranınıza eklendi.

![image](https://user-images.githubusercontent.com/80598532/149423399-1af83c8a-4fd3-42f2-997d-b784fd814a69.png)

