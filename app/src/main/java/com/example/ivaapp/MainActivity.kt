package com.example.ivaapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Configuración de la vista para manejar los bordes y márgenes del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialización de la UI
        val spinner: Spinner = findViewById(R.id.sp_type)
        val btnCalculate: Button = findViewById(R.id.btn_calculate)
        val etAmount: EditText = findViewById(R.id.et_amount)
        val tvTotal: TextView = findViewById(R.id.tv_total)

        // Configuramos el Spinner con opciones de IVA
        val ivaTypes = arrayOf("IVA Normal (16%)", "IVA Fronterizo (10%)")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ivaTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Función para calcular el IVA y actualizar el total
        btnCalculate.setOnClickListener {
            val amountText = etAmount.text.toString()
            if (amountText.isNotEmpty()) {
                val amount = amountText.toDouble()
                val ivaRate = when (spinner.selectedItemPosition) {
                    0 -> 0.16 // IVA normal 16%
                    1 -> 0.10 // IVA fronterizo 10%
                    else -> 0.0
                }

                val totalAmount = amount * (1 + ivaRate)
                tvTotal.text = "$${"%.2f".format(totalAmount)}"
            } else {
                tvTotal.text = "Ingresa un monto válido"
            }
        }
    }
}
