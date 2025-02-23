const mongoose = require("mongoose");

const productSchema = new mongoose.Schema(
  {
    name: { type: String, required: true },
    brand_id: { type: mongoose.Schema.Types.ObjectId, ref: "Brand", required: true },
    category_id: { type: mongoose.Schema.Types.ObjectId, ref: "Category", required: true },
    price: { type: Number, required: true },
    description: { type: String },
    image_urls: { type: [String], required: true }, // Mảng URL ảnh
  },
  { timestamps: true }
);

const Product = mongoose.model("Product", productSchema);

module.exports = Product;
