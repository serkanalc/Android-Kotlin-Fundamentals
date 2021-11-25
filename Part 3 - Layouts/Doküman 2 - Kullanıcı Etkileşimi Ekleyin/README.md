# Kullanıcı Etkileşimi Ekleyin

- [Metin girişi İçin Bir EditText Ekleyin](#1)
- [EditText'inize Stil Verin](#2)
- [Bir Düğme Ekleyin ve Ona Stil Verin](#3)
- [Nickname Görüntülemek İçin Bir TextView Ekleyin](#4)
- [TAMAMLANDI Düğmesine Bir Click Listener Ekleyin](#5)
- [TextView Nickname'ine bir Click Listener Ekleyin](#6)


Bu dokümanda, Kullanıcı etkileşimi eklemek için AboutMe uygulamasını genişletirsiniz. Nickname görüntülemek için nickname alanı, bir **DONE** düğmesi ve bir metin görünümü eklersiniz. Kullanıcı bir nickname girip **DONE** düğmesine dokunduğunda, metin görünümü girilen nickname'i gösterecek şekilde güncellenir. Kullanıcı, metin görünümüne dokunarak takma adı tekrar güncelleyebilir.

![image](https://user-images.githubusercontent.com/70329389/143226650-2669c75d-2897-472c-8a9f-93182477c3c7.png)


## <a name="1"></a>Metin girişi İçin Bir EditText Ekleyin

Bu görevde, kullanıcının bir nickname(takma ad) girmesine izin vermek için bir EditText giriş alanı eklersiniz.

### Aşama 1 : Başlangıç

1. Önceki bir Dokümandan AboutMe uygulamasına sahip değilseniz, [AboutMeInteractive-Starter](https://github.com/serkanalc/Android-Kotlin-Fundamentals-Projects/tree/main/AboutMe%20-%20Project) başlangıç kodunu indirin. Bu, önceki Dokümanda bitirdiğiniz kodun aynısı.

2. Android Studio'da AboutMeInteractive-Starter projesini açın.
3. Uygulamayı çalıştırın. Kaydırma görünümünde bir ad metni görünümü, bir yıldız resmi ve uzun bir metin parçası görürsünüz.

![image](https://user-images.githubusercontent.com/70329389/143229037-deab1ec9-2a36-4f51-9bb4-ee845f587a52.png)

Kullanıcı uygulamayla etkileşime girebiliyorsa, örneğin kullanıcı metin girebiliyorsa, uygulamalar daha ilginçtir. Metin girişini kabul etmek için Android, metin düzenleme adı verilen bir kullanıcı arabirimi (UI) widget'ı sağlar. TextView'in bir alt sınıfı olan **EditText'i** kullanarak bir düzenleme metni tanımlarsınız. Düzenleme metni, kullanıcının aşağıdaki ekran görüntüsünde gösterildiği gibi metin girişini girmesine ve değiştirmesine olanak tanır.

![image](https://user-images.githubusercontent.com/70329389/143229579-649fca35-53e3-4cb8-bda3-626a6c81e4ca.png)

### Aşama 1 : Edittext Ekleyin

1. Android Studio'da, Design sekmesinde **aktivite_main.xml** layout dosyasını açın.
2. Palet bölmesinde Metin'e tıklayın.

![image](https://user-images.githubusercontent.com/70329389/143229780-a6ce2fc4-791c-4064-9835-a4bc678be678.png)

Bir TextView olan Ab **TextView**, **Palet** bölmesindeki metin öğeleri listesinin en üstünde gösterilir. Ab **TextView**'in altında birden çok EditText görünümü vardır.

Palet bölmesinde, **TextView** simgesinin Ab harflerini alt çizgi olmadan nasıl gösterdiğine dikkat edin. Ancak **EditText** simgeleri, Ab'nin altı çizili olarak gösterilir. Alt çizgi, görünümün düzenlenebilir olduğunu gösterir.

Android, EditText görünümlerinin her biri için farklı nitelikler ayarlar ve sistem uygun yazılım giriş yöntemini (ekran klavyesi gibi) görüntüler.

3. Bir **PlainText** düzenleme metnini Bileşen Ağacına sürükleyin ve onu **name_text**'in altına ve **star_image**'in üstüne yerleştirin.

![image](https://user-images.githubusercontent.com/70329389/143230316-9ecb54ed-b14b-45ba-bc25-b1e184f263fa.png)

4. EditText görünümünde aşağıdaki nitelikleri ayarlamak için **Attributes** bölmesini kullanın.

| Attribute | Value |
|---|---|
| id | nickname_edit |
| layout_width | match_parent (default) |
| layout_height | wrap_content (default) |

5. Uygulamanızı çalıştırın. Yıldız görüntüsünün üzerinde, varsayılan "Name" metnine sahip bir düzenleme metni görürsünüz.

![image](https://user-images.githubusercontent.com/70329389/143231208-6ad92d7f-4376-4254-ab6a-24008acd0e64.png)

## <a name="2"></a>EditText'inize Stil Verin

Bu aşamada, bir hint ekleyerek, metin hizalamasını değiştirerek, stili **NameStyle** olarak değiştirerek ve giriş türünü ayarlayarak **EditText** görünümünüzü şekillendirirsiniz.

### Aşama 1 : Bid Hint Text Ekleyin

1. **string.xml** dosyasındaki ipucu için yeni bir dize kaynağı ekleyin.

```
<string name="what_is_your_nickname">What is your Nickname?</string>
```

> İpucu: Kullanıcıların düzenlenebilir alanlar için hangi girdinin beklendiğini anlamalarına yardımcı olmak için her EditText görünümünde bir ipucu göstermek iyi bir uygulamadır.

2. Aşağıdaki nitelikleri **EditText** görünümüne ayarlamak için **Attriburtes** bölmesini kullanın:

| Attribute | Value |
|---|---|
| style | @style/NameStyle |
| textAlignment | (center) |
| hint | @string/what_is_your_nickname |

3. **Attributes** bölmesinde, **text** özelliğinden **Name** değerini kaldırın. İpucunun görüntülenmesi için text öznitelik değerinin boş olması gerekir.

### Aşama 2 : inputType Niteliğini Ayarlayın

**inputType** özelliği, kullanıcıların **EditText** görünümüne girebilecekleri giriş türünü belirtir. Android sistemi, ayarlanan giriş tipine bağlı olarak uygun giriş alanını ve ekran klavyesini görüntüler.

Tüm olası giriş türlerini görmek için, **Attributes** bölmesinde **inputType** alanını tıklayın veya alanın yanındaki üç noktayı tıklayın. Kullanabileceğiniz tüm giriş türlerini, o anda etkin olan giriş türü işaretli olarak gösteren bir liste açılır. Birden fazla giriş tipi seçebilirsiniz.

![image](https://user-images.githubusercontent.com/70329389/143233624-2241b002-79df-411d-b810-24e5314f46c9.png)

Örneğin, parolalar için **textPassword** değerini kullanın. Metin alanı, kullanıcının girişini gizler.

![image](https://user-images.githubusercontent.com/70329389/143233678-17665421-0bc4-48f8-8caf-d20884f84a44.png)

Telefon numaraları için **phone** değerini kullanın. Bir sayı tuş takımı görüntülenir ve kullanıcı yalnızca sayıları girebilir.

![image](https://user-images.githubusercontent.com/70329389/143233763-c2694761-ed19-4522-a289-ae54445a2eb4.png)

Takma ad alanı için giriş türünü ayarlayın:

1. **Nick_edit** düzenleme metni için **inputType** özniteliğini **textPersonName** olarak ayarlayın.
2. Component Tree bölmesinde, bir **autoFillHints** uyarısına dikkat edin. Bu uyarı, bu uygulama için geçerli değildir ve bu döküman kapsamı dışındadır, dolayısıyla onu görmezden gelebilirsiniz.
3. Attributes bölmesinde, **EditText** görünümünün aşağıdaki nitelikleri için değerleri doğrulayın:


| Attribute | Value |
|---|---|
| id | nickname_edit |
| layout_width | match_parent (default) |
| layout_height | wrap_content (default) |
| style | @style/NameStyle |
| inputType | textPersonName |
| hint | "@string/what_is_your_nickname" |
| text | *(empty)* |

## <a name="3"></a>Bir Düğme Ekleyin ve Ona Stil Verin

**Button**, kullanıcının bir eylemi gerçekleştirmek için dokunabileceği bir UI öğesidir. Bir Button, Text'ten, bir icon veya hem text hem de bir icondan oluşabilir.

![image](https://user-images.githubusercontent.com/70329389/143239981-d48e556d-b14f-4e2b-b007-a4a9033bcb29.png)

Bu bölümded, kullanıcının bir takma ad girdikten sonra dokunduğu bir Done düğmesi eklersiniz. Düğme, **EditText** görünümünü takma adı görüntüleyen bir **TextView** görünümüyle değiştirir. Takma adı güncellemek için kullanıcı TextView görünümüne dokunabilir.

### Aşama 1 : "TAMAMLANDI" ButtonU Ekleyin

1. Palet bölmesinden Component Tree'e bir düğme sürükleyin. Düğmeyi nick_edit edit text'in altına yerleştirin.

![image](https://user-images.githubusercontent.com/70329389/143240339-5b84af2e-81c0-4caf-acad-d588b30b952d.png)

2. **TAMAMLANDI** adlı yeni bir dize kaynağı oluşturun. Dizeye **TAMAMLANDI** değerini verin,

```
<string name="done">Tamamlandı</string>
```
3. Yeni eklenen Düğme görünümünde aşağıdaki nitelikleri ayarlamak için **Attributes** bölmesini kullanın:

| Attribute | Value |
|---|---|
| id | done_button |
| text | @string/done |
| layout_gravity | center_horizontal |
| layout_width | wrap_content |

**layout_gravity** niteliği, görünümü üst düzen olan **LinearLayout**'ta ortalar.

4. Style'ı, Android'in sağladığı önceden tanımlanmış stillerden biri olan **Widget.AppCompat.Button.Colored** olarak değiştirin. Stili açılır menüden veya Kaynaklar penceresinden seçebilirsiniz.

![image](https://user-images.githubusercontent.com/70329389/143241495-ccb4da5a-2d0e-4056-ad02-e2b5189e2971.png)

Bu stil, düğme rengini vurgu rengi olan **colorAccent** olarak değiştirir. Vurgu rengi **res/values/colors.xml** dosyasında tanımlanır.

![image](https://user-images.githubusercontent.com/70329389/143241828-f0f5fd6e-3162-4d6e-92f8-9ab0be192d43.png)

**Colours.xml** dosyası, uygulamanız için varsayılan renkleri içerir. Uygulamanızın gereksinimlerine göre projenizde yeni renk kaynakları ekleyebilir veya mevcut renk kaynaklarını değiştirebilirsiniz.

Örnek color.xml dosyası:

```
<?xml version="1.0" encoding="utf-8"?>
<resources>
   <color name="colorPrimary">#008577</color>
   <color name="colorPrimaryDark">#00574B</color>
   <color name="colorAccent">#D81B60</color>
</resources>
```

### Aşama 2 : Tamamlandı Buttonunun Style'ını Belirleyin

1. Attribbutes bölmesinde, **Layout_Margin > Top** öğesini seçerek bir üst kenar boşluğu ekleyin. Üst kenar boşluğunu dimens.xml dosyasında tanımlanan layout_margin olarak ayarlayın.

![image](https://user-images.githubusercontent.com/70329389/143242391-89abea15-f2ed-47b3-80f8-0cbe0140cffc.png)

2. Açılır menüden **fontFamil**y özniteliğini **roboto** olarak ayarlayın.

![image](https://user-images.githubusercontent.com/70329389/143242597-2055370e-273b-4bca-b335-7dced92d024d.png)

3. Text sekmesine geçin ve yeni eklenen düğme için oluşturulan XML kodunu doğrulayın.

```
<Button
   android:id="@+id/done_button"
   style="@style/Widget.AppCompat.Button.Colored"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   android:layout_gravity="center_horizontal"
   android:layout_marginTop="@dimen/layout_margin"
   android:fontFamily="@font/roboto"
   android:text="@string/done" />
```

### Aşama 3 : Renk Kaynağını Değiştir

Bu aşamada, etkinliğinizin uygulama çubuğuna uyması için düğmenin vurgu rengini değiştirirsiniz.

1. **res/values/colors.xml** dosyasını açın ve **colorAccent** değerini #76bf5e olarak değiştirin.

```
<color name="colorAccent">#76bf5e</color>
```

HEX koduna karşılık gelen rengi dosya düzenleyicinin sol kenar boşluğunda görebilirsiniz.

![image](https://user-images.githubusercontent.com/70329389/143257551-39b52d39-0a26-4747-9c9a-c32fa55eeab7.png)


Tasarım düzenleyicideki düğme rengindeki değişikliğe dikkat edin.

2. Uygulamanızı çalıştırın. Düzenleme metninin altında stil sahibi bir BİTTİ düğmesi görmelisiniz.

![image](https://user-images.githubusercontent.com/70329389/143257444-f2a7ad17-04a0-4bf2-9c70-4363652970df.png)



## <a name="4"></a>Nickname Görüntülemek İçin Bir TextView Ekleyin

Kullanıcı bir takma ad girip TAMAMLANDI düğmesine dokunduktan sonra, takma ad bir TextView görünümünde görüntülenir. Bu görevde, renkli bir arka plana sahip bir metin görünümü eklersiniz. Metin görünümü, kullanıcının takma adını star_image'in üzerinde görüntüler.

### Adım 1: Takma Ad İçin Bir TextView Ekleyin

1. Palet bölmesinden bir text view'i **Component Tree** sürükleyin. text view'ünü **done_button**'ın altına ve **star_image**'ın üstüne yerleştirin.

![image](https://user-images.githubusercontent.com/70329389/143261950-5625191b-4c59-4290-8c05-ff33bc0547e2.png)

2. Yeni TextView görünümü için aşağıdaki nitelikleri ayarlamak için **Attributes** bölmesini kullanın:

| Attribute | Value |
|---|---|
| id | nickname_text |
| style | NameStyle |
| textAlignment | *(center)* |

Adım 2: TextView'in görünürlüğünü değiştirin

Views özelliğini kullanarak uygulamanızda viewlerini gösterebilir veya gizleyebilirsiniz. Bu attribüte'lar üç değerden birini alır:

- **visible**: Görünüm görünür.
- **Invisible**: Görünümü gizler, ancak görünüm yine de layoutta yer kaplar.
- **Gone**: Görünümü gizler ve görünüm, layoutta hiç yer kaplamaz.

1. **Attributes** bölmesinde, uygulamanızın ilk başta bu metin görünümünü göstermesini istemediğiniz için **nick_text** metin görünümünün visibility'sini **Gone** olarak ayarlayın.

![image](https://user-images.githubusercontent.com/70329389/143264281-d6712320-74a2-4afc-a66c-81c2a5633e5e.png)

Attributes bölmesinde özniteliği değiştirdiğinizde, **nick_metin** görünümünün tasarım düzenleyicisinden kaybolduğuna dikkat edin. Görünüm, layout preview gizlidir.

2. **Nick_text** görünümünün text öznitelik değerini boş bir stringle değiştirin.

Bu **TextView** için oluşturulan XML kodunuz şuna benzer görünmelidir:

```
<TextView
   android:id="@+id/nickname_text"
   style="@style/NameStyle"
   android:layout_width="match_parent"
   android:layout_height="wrap_content"
   android:textAlignment="center"
   android:visibility="gone"
   android:text="" />
```

layout preview aşağıdaki gibi görünmelidir:

![image](https://user-images.githubusercontent.com/70329389/143265177-0569015d-3b72-492b-9f96-64cf548d8bd0.png)


## <a name="5"></a>TAMAMLANDI Düğmesine Bir Click Listener Ekleyin

Düğme nesnesindeki (veya herhangi bir görünümdeki) tıklama işleyicisi, düğmeye (görünüm) dokunulduğunda gerçekleştirilecek eylemi belirtir. Click olayını işleyen işlev, düğmeyle (görünüm) düzeni barındıran Activity'de uygulanmalıdır.

```
private fun clickHandlerFunction(viewThatIsClicked: View) {
// Düğme tıklama olayını gerçekleştirmek için kod ekleyin
}
```

Tıklama dinleyicisi genel olarak bu biçime sahiptir; burada geçirilen görünüm, tıklamayı veya dokunmayı alan görünümdür.

Tıklama-dinleyici(click-listener) işlevini düğme tıklama olaylarına iki şekilde ekleyebilirsiniz:

- XML düzeninde, `<Button>` öğesine **android:onClick** niteliğini ekleyebilirsiniz. Örneğin:

```
<Button
   android:id="@+id/done_button"
   android:text="@string/done"
   ...
   android:onClick="clickHandlerFunction"/>
  
```

- SetOnClickListener'ı çağırarak, Aktivitenin onCreate() içinde, çalışma zamanında programlı olarak yapabilirsiniz. Örneğin:

```
myButton.setOnClickListener {
   clickHanderFunction(it)
}
```

Bu görevde **done_button** için programlı olarak bir tıklama dinleyicisi eklersiniz. Tıklama dinleyicisini, **MainActivity.kt** olan ilgili activity'e eklersiniz.

addNickname adlı tıklama dinleyici işleviniz aşağıdakileri yapacaktır:

- Nick_edit düzenleme metninden metni alın.
- Nick_text metin görünümündeki metni ayarlayın.
- Düzenleme metnini ve düğmeyi gizleyin.
- TextView takma adını görüntüleyin.

### Adım 1 : Tıklama Dinleyicisi (Click Listener) Ekleyin

1. Android Studio'da, **Java** klasöründe **MainActivity.kt** dosyasını açın.
2. **MainActivity.kt**'de, MainActivity sınıfının içine **addNickname** adlı bir function ekleyin. Görünüm türünde **view** adlı bir giriş parametresi ekleyin. Görünüm parametresi, işlevin çağrıldığı Görünümdür. Bu durumda görünüm, TAMAMLANDI butonunuzun bir örneği olacaktır.

```
private fun addNickname(view: View) {
}
```
3. **addNickname** function'ın içinde, **nick_edit** düzenleme metnine ve **nick_text** metin görünümüne bir referans almak için **findViewById()** kullanın.

```
val editText = findViewById<EditText>(R.id.nickname_edit)
val nicknameTextView = findViewById<TextView>(R.id.nickname_text)
```

4. **NickTextView** metin görünümündeki text'i, kullanıcının text özelliğinden alarak **editText**'e girdiği metne ayarlayın.

```
nicknameTextView.text = editText.text
```
5. editText'in görünürlük özelliğini **View.GONE** olarak ayarlayarak **EditText** görünümünü takma adını gizleyin.

Önceki bir aşamada, Layout Editor'yi kullanarak **visibility** özelliğini değiştirmiştiniz. Burada aynı şeyi programlı olarak yaparsınız.

```
editText.visibility = View.GONE
```

6. **Visibility** özelliğini **View.GONE** olarak ayarlayarak **TAMAMLANDI** düğmesini gizleyin. Function'un giriş parametresi olarak düğmenin referansına zaten sahipsiniz, görünüm.

```
view.visibility = View.GONE
```

**addNickname** function'unun sonunda, visibility özelliğini **View.VISIBLE** olarak ayarlayarak takma adı **TextView** görünümünü görünür yapın.

```
nicknameTextView.visibility = View.VISIBLE
```

### Aşama 2 : Tıklama Dinleyicisini(Click Listener) TAMAMLANDI Düğmesine Bağlayın






