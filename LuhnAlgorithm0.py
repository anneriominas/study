card_number = "4111 1111 1111 1111"  # Имитируем ввод пользователя

# 1. Удалить пробелы и дефисы из строки
clear_number = card_number.replace(" ", "").replace("-", "")

# 2. Строка состоит только из цифр?
if not clear_number.isdigit():
    print("Ошибка ввода: строка должна содержать только цифры!")
    # Здесь в будущем будет return False
    is_valid = False
else:
    # Если проверка пройдена, выполняем основной алгоритм Луна
    
    
    # 3. Инициализировать переменные
    total_sum = 0
    alternate = False
    
    # 4. Цикл с конца строки
    for i in range(len(clear_number) - 1, -1, -1):
        # 6. Перевести символ в цифру
        n = int(clear_number[i])
        
        # 7. Переменная alternate == true?
        if alternate:
            # 8. Умножить цифру на 2
            n = n * 2
            # 9. n > 9 ?
            if n > 9:
                # 10. n = n - 9
                n = n - 9
                
        # 11. Прибавить к общей сумме
        total_sum = total_sum + n
        
        # 12. Инвертировать флаг
        alternate = not alternate
        
    
    # 5. sum % 10 == 0 ?
    if total_sum % 10 == 0:
        is_valid = True
    else:
        is_valid = False


print(f"Результат проверки карты {card_number}: {is_valid}")
print("Знакомство с функциями")
