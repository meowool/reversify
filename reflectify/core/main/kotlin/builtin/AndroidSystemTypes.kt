/*
 * Copyright (C) 2024 Meowool <https://github.com/meowool/graphs/contributors>
 *
 * This file is part of the Ping project <https://github.com/meowool/ping>.
 *
 * Ping is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Ping is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Ping.  If not, see <https://www.gnu.org/licenses/>.
 */

@file:Suppress("SpellCheckingInspection")

package com.meowool.reflectify.builtin

import android.app.ActionBar
import android.app.Activity
import android.app.AlertDialog
import android.app.AppComponentFactory
import android.app.Dialog
import android.app.Service
import android.content.BroadcastReceiver
import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.res.AssetManager
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.Spannable
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.AbsListView
import android.widget.Adapter
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EdgeEffect
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.RemoteViews
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.meowool.reflectify.C
import com.meowool.reflectify.Type

/**
 * The type-safety representation of type [View].
 *
 * This is represented as `Landroid/view/View;` in bytecode.
 */
inline val C.Companion.View: Type<View>
  get() = Type<View>()

/**
 * The type-safety representation of type [Menu].
 *
 * This is represented as `Landroid/view/Menu;` in bytecode.
 */
inline val C.Companion.Menu: Type<Menu>
  get() = Type<Menu>()

/**
 * The type-safety representation of type [MenuItem].
 *
 * This is represented as `Landroid/view/MenuItem;` in bytecode.
 */
inline val C.Companion.MenuItem: Type<MenuItem>
  get() = Type<MenuItem>()

/**
 * The type-safety representation of type [MenuInflater].
 *
 * This is represented as `Landroid/view/MenuInflater;` in bytecode.
 */
inline val C.Companion.MenuInflater: Type<MenuInflater>
  get() = Type<MenuInflater>()

/**
 * The type-safety representation of type [Button].
 *
 * This is represented as `Landroid/widget/Button;` in bytecode.
 */
inline val C.Companion.Button: Type<Button>
  get() = Type<Button>()

/**
 * The type-safety representation of type [TextView].
 *
 * This is represented as `Landroid/widget/TextView;` in bytecode.
 */
inline val C.Companion.TextView: Type<TextView>
  get() = Type<TextView>()

/**
 * The type-safety representation of type [EditText].
 *
 * This is represented as `Landroid/widget/EditText;` in bytecode.
 */
inline val C.Companion.EditText: Type<EditText>
  get() = Type<EditText>()

/**
 * The type-safety representation of type [CheckBox].
 *
 * This is represented as `Landroid/widget/CheckBox;` in bytecode.
 */
inline val C.Companion.CheckBox: Type<CheckBox>
  get() = Type<CheckBox>()

/**
 * The type-safety representation of type [ImageView].
 *
 * This is represented as `Landroid/widget/ImageView;` in bytecode.
 */
inline val C.Companion.ImageView: Type<ImageView>
  get() = Type<ImageView>()

/**
 * The type-safety representation of type [ImageButton].
 *
 * This is represented as `Landroid/widget/ImageButton;` in bytecode.
 */
inline val C.Companion.ImageButton: Type<ImageButton>
  get() = Type<ImageButton>()

/**
 * The type-safety representation of type [RemoteViews].
 *
 * This is represented as `Landroid/widget/RemoteViews;` in bytecode.
 */
inline val C.Companion.RemoteViews: Type<RemoteViews>
  get() = Type<RemoteViews>()

/**
 * The type-safety representation of type [ListView].
 *
 * This is represented as `Landroid/widget/ListView;` in bytecode.
 */
inline val C.Companion.ListView: Type<ListView>
  get() = Type<ListView>()

/**
 * The type-safety representation of type [PopupWindow].
 *
 * This is represented as `Landroid/widget/PopupWindow;` in bytecode.
 */
inline val C.Companion.PopupWindow: Type<PopupWindow>
  get() = Type<PopupWindow>()

/**
 * The type-safety representation of type [EdgeEffect].
 *
 * This is represented as `Landroid/widget/EdgeEffect;` in bytecode.
 */
inline val C.Companion.EdgeEffect: Type<EdgeEffect>
  get() = Type<EdgeEffect>()

/**
 * The type-safety representation of type [ScrollView].
 *
 * This is represented as `Landroid/widget/ScrollView;` in bytecode.
 */
inline val C.Companion.ScrollView: Type<ScrollView>
  get() = Type<ScrollView>()

/**
 * The type-safety representation of type [Spinner].
 *
 * This is represented as `Landroid/widget/Spinner;` in bytecode.
 */
inline val C.Companion.Spinner: Type<Spinner>
  get() = Type<Spinner>()

/**
 * The type-safety representation of type [Toast].
 *
 * This is represented as `Landroid/widget/Toast;` in bytecode.
 */
inline val C.Companion.Toast: Type<Toast>
  get() = Type<Toast>()

/**
 * The type-safety representation of type [KeyEvent].
 *
 * This is represented as `Landroid/view/KeyEvent;` in bytecode.
 */
inline val C.Companion.KeyEvent: Type<KeyEvent>
  get() = Type<KeyEvent>()

/**
 * The type-safety representation of type [MotionEvent].
 *
 * This is represented as `Landroid/view/MotionEvent;` in bytecode.
 */
inline val C.Companion.MotionEvent: Type<MotionEvent>
  get() = Type<MotionEvent>()

/**
 * The type-safety representation of type [ViewGroup].
 *
 * This is represented as `Landroid/view/ViewGroup;` in bytecode.
 */
inline val C.Companion.ViewGroup: Type<ViewGroup>
  get() = Type<ViewGroup>()

/**
 * The type-safety representation of type [ViewGroup.LayoutParams].
 *
 * This is represented as `Landroid/view/ViewGroup$LayoutParams;` in bytecode.
 */
inline val C.Companion.ViewGroup_LayoutParams: Type<ViewGroup.LayoutParams>
  get() = Type<ViewGroup.LayoutParams>()

/**
 * The type-safety representation of type [ViewGroup.MarginLayoutParams].
 *
 * This is represented as `Landroid/view/ViewGroup$MarginLayoutParams;` in bytecode.
 */
inline val C.Companion.ViewGroup_MarginLayoutParams: Type<ViewGroup.MarginLayoutParams>
  get() = Type<ViewGroup.MarginLayoutParams>()

/**
 * The type-safety representation of type [View.OnClickListener].
 *
 * This is represented as `Landroid/view/View$OnClickListener;` in bytecode.
 */
inline val C.Companion.View_OnClickListener: Type<View.OnClickListener>
  get() = Type<View.OnClickListener>()

/**
 * The type-safety representation of type [View.OnLongClickListener].
 *
 * This is represented as `Landroid/view/View$OnLongClickListener;` in bytecode.
 */
inline val C.Companion.View_OnLongClickListener: Type<View.OnLongClickListener>
  get() = Type<View.OnLongClickListener>()

/**
 * The type-safety representation of type [View.OnTouchListener].
 *
 * This is represented as `Landroid/view/View$OnTouchListener;` in bytecode.
 */
inline val C.Companion.View_OnTouchListener: Type<View.OnTouchListener>
  get() = Type<View.OnTouchListener>()

/**
 * The type-safety representation of type [LayoutInflater].
 *
 * This is represented as `Landroid/view/LayoutInflater;` in bytecode.
 */
inline val C.Companion.LayoutInflater: Type<LayoutInflater>
  get() = Type<LayoutInflater>()

/**
 * The type-safety representation of type [LayoutInflater.Factory].
 *
 * This is represented as `Landroid/view/LayoutInflater$Factory;` in bytecode.
 */
inline val C.Companion.LayoutInflater_Factory: Type<LayoutInflater.Factory>
  get() = Type<LayoutInflater.Factory>()

/**
 * The type-safety representation of type [LayoutInflater.Factory2].
 *
 * This is represented as `Landroid/view/LayoutInflater$Factory2;` in bytecode.
 */
inline val C.Companion.LayoutInflater_Factory2: Type<LayoutInflater.Factory2>
  get() = Type<LayoutInflater.Factory2>()

/**
 * The type-safety representation of type [LinearLayout].
 *
 * This is represented as `Landroid/widget/LinearLayout;` in bytecode.
 */
inline val C.Companion.LinearLayout: Type<LinearLayout>
  get() = Type<LinearLayout>()

/**
 * The type-safety representation of type [LinearLayout.LayoutParams].
 *
 * This is represented as `Landroid/widget/LinearLayout$LayoutParams;` in bytecode.
 */
inline val C.Companion.LinearLayout_LayoutParams: Type<LinearLayout.LayoutParams>
  get() = Type<LinearLayout.LayoutParams>()

/**
 * The type-safety representation of type [FrameLayout].
 *
 * This is represented as `Landroid/widget/FrameLayout;` in bytecode.
 */
inline val C.Companion.FrameLayout: Type<FrameLayout>
  get() = Type<FrameLayout>()

/**
 * The type-safety representation of type [FrameLayout.LayoutParams].
 *
 * This is represented as `Landroid/widget/FrameLayout$LayoutParams;` in bytecode.
 */
inline val C.Companion.FrameLayout_LayoutParams: Type<FrameLayout.LayoutParams>
  get() = Type<FrameLayout.LayoutParams>()

/**
 * The type-safety representation of type [RelativeLayout].
 *
 * This is represented as `Landroid/widget/RelativeLayout;` in bytecode.
 */
inline val C.Companion.RelativeLayout: Type<RelativeLayout>
  get() = Type<RelativeLayout>()

/**
 * The type-safety representation of type [RelativeLayout.LayoutParams].
 *
 * This is represented as `Landroid/widget/RelativeLayout$LayoutParams;` in bytecode.
 */
inline val C.Companion.RelativeLayout_LayoutParams: Type<RelativeLayout.LayoutParams>
  get() = Type<RelativeLayout.LayoutParams>()

/**
 * The type-safety representation of type [Context].
 *
 * This is represented as `Landroid/content/Context;` in bytecode.
 */
inline val C.Companion.Context: Type<Context>
  get() = Type<Context>()

/**
 * The type-safety representation of type [ContentProvider].
 *
 * This is represented as `Landroid/content/ContentProvider;` in bytecode.
 */
inline val C.Companion.ContentProvider: Type<ContentProvider>
  get() = Type<ContentProvider>()

/**
 * The type-safety representation of type [Service].
 *
 * This is represented as `Landroid/app/Service;` in bytecode.
 */
inline val C.Companion.Service: Type<Service>
  get() = Type<Service>()

/**
 * The type-safety representation of type [Window].
 *
 * This is represented as `Landroid/view/Window;` in bytecode.
 */
inline val C.Companion.Window: Type<Window>
  get() = Type<Window>()

/**
 * The type-safety representation of type [WindowManager].
 *
 * This is represented as `Landroid/view/WindowManager;` in bytecode.
 */
inline val C.Companion.WindowManager: Type<WindowManager>
  get() = Type<WindowManager>()

/**
 * The type-safety representation of type [Parcelable].
 *
 * This is represented as `Landroid/os/Parcelable;` in bytecode.
 */
inline val C.Companion.Parcelable: Type<Parcelable>
  get() = Type<Parcelable>()

/**
 * The type-safety representation of type [ContentResolver].
 *
 * This is represented as `Landroid/content/ContentResolver;` in bytecode.
 */
inline val C.Companion.ContentResolver: Type<ContentResolver>
  get() = Type<ContentResolver>()

/**
 * The type-safety representation of type [BroadcastReceiver].
 *
 * This is represented as `Landroid/content/BroadcastReceiver;` in bytecode.
 */
inline val C.Companion.BroadcastReceiver: Type<BroadcastReceiver>
  get() = Type<BroadcastReceiver>()

/**
 * The type-safety representation of type [ActionBar].
 *
 * This is represented as `Landroid/app/ActionBar;` in bytecode.
 */
inline val C.Companion.ActionBar: Type<ActionBar>
  get() = Type<ActionBar>()

/**
 * The type-safety representation of type [ContextWrapper].
 *
 * This is represented as `Landroid/content/ContextWrapper;` in bytecode.
 */
inline val C.Companion.ContextWrapper: Type<ContextWrapper>
  get() = Type<ContextWrapper>()

/**
 * The type-safety representation of type [ApplicationInfo].
 *
 * This is represented as `Landroid/content/pm/ApplicationInfo;` in bytecode.
 */
inline val C.Companion.ApplicationInfo: Type<ApplicationInfo>
  get() = Type<ApplicationInfo>()

/**
 * The type-safety representation of type [ContentValues].
 *
 * This is represented as `Landroid/content/ContentValues;` in bytecode.
 */
inline val C.Companion.ContentValues: Type<ContentValues>
  get() = Type<ContentValues>()

/**
 * The type-safety representation of type [PackageInfo].
 *
 * This is represented as `Landroid/content/pm/PackageInfo;` in bytecode.
 */
inline val C.Companion.PackageInfo: Type<PackageInfo>
  get() = Type<PackageInfo>()

/**
 * The type-safety representation of type [Bundle].
 *
 * This is represented as `Landroid/os/Bundle;` in bytecode.
 */
inline val C.Companion.Bundle: Type<Bundle>
  get() = Type<Bundle>()

/**
 * The type-safety representation of type [Activity].
 *
 * This is represented as `Landroid/app/Activity;` in bytecode.
 */
inline val C.Companion.Activity: Type<Activity>
  get() = Type<Activity>()

/**
 * The type-safety representation of type [Intent].
 *
 * This is represented as `Landroid/content/Intent;` in bytecode.
 */
inline val C.Companion.Intent: Type<Intent>
  get() = Type<Intent>()

/**
 * The type-safety representation of type [AppComponentFactory].
 *
 * This is represented as `Landroid/app/AppComponentFactory;` in bytecode.
 */
inline val C.Companion.AppComponentFactory: Type<AppComponentFactory>
  get() = Type<AppComponentFactory>()

/**
 * The type-safety representation of type [AlertDialog].
 *
 * This is represented as `Landroid/app/AlertDialog;` in bytecode.
 */
inline val C.Companion.AlertDialog: Type<AlertDialog>
  get() = Type<AlertDialog>()

/**
 * The type-safety representation of type [Dialog].
 *
 * This is represented as `Landroid/app/Dialog;` in bytecode.
 */
inline val C.Companion.Dialog: Type<Dialog>
  get() = Type<Dialog>()

/**
 * The type-safety representation of type [DialogInterface].
 *
 * This is represented as `Landroid/content/DialogInterface;` in bytecode.
 */
inline val C.Companion.DialogInterface: Type<DialogInterface>
  get() = Type<DialogInterface>()

/**
 * The type-safety representation of type [Drawable].
 *
 * This is represented as `Landroid/graphics/drawable/Drawable;` in bytecode.
 */
inline val C.Companion.Drawable: Type<Drawable>
  get() = Type<Drawable>()

/**
 * The type-safety representation of type [Editable].
 *
 * This is represented as `Landroid/text/Editable;` in bytecode.
 */
inline val C.Companion.Editable: Type<Editable>
  get() = Type<Editable>()

/**
 * The type-safety representation of type [Spannable].
 *
 * This is represented as `Landroid/text/Spannable;` in bytecode.
 */
inline val C.Companion.Spannable: Type<Spannable>
  get() = Type<Spannable>()

/**
 * The type-safety representation of type [AssetManager].
 *
 * This is represented as `Landroid/content/res/AssetManager;` in bytecode.
 */
inline val C.Companion.AssetManager: Type<AssetManager>
  get() = Type<AssetManager>()

/**
 * The type-safety representation of type [ColorStateList].
 *
 * This is represented as `Landroid/content/res/ColorStateList;` in bytecode.
 */
inline val C.Companion.ColorStateList: Type<ColorStateList>
  get() = Type<ColorStateList>()

/**
 * The type-safety representation of type [Typeface].
 *
 * This is represented as `Landroid/graphics/Typeface;` in bytecode.
 */
inline val C.Companion.Typeface: Type<Typeface>
  get() = Type<Typeface>()

/**
 * The type-safety representation of type [Bitmap].
 *
 * This is represented as `Landroid/graphics/Bitmap;` in bytecode.
 */
inline val C.Companion.Bitmap: Type<Bitmap>
  get() = Type<Bitmap>()

/**
 * The type-safety representation of type [Paint].
 *
 * This is represented as `Landroid/graphics/Paint;` in bytecode.
 */
inline val C.Companion.Paint: Type<Paint>
  get() = Type<Paint>()

/**
 * The type-safety representation of type [AttributeSet].
 *
 * This is represented as `Landroid/util/AttributeSet;` in bytecode.
 */
inline val C.Companion.AttributeSet: Type<AttributeSet>
  get() = Type<AttributeSet>()

/**
 * The type-safety representation of type [Adapter].
 *
 * This is represented as `Landroid/widget/Adapter;` in bytecode.
 */
inline val C.Companion.Adapter: Type<Adapter>
  get() = Type<Adapter>()

/**
 * The type-safety representation of type [BaseAdapter].
 *
 * This is represented as `Landroid/widget/BaseAdapter;` in bytecode.
 */
inline val C.Companion.BaseAdapter: Type<BaseAdapter>
  get() = Type<BaseAdapter>()

/**
 * The type-safety representation of type [AbsListView].
 *
 * This is represented as `Landroid/widget/AbsListView;` in bytecode.
 */
inline val C.Companion.AbsListView: Type<AbsListView>
  get() = Type<AbsListView>()
